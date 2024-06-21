package ge.homework.service;

import ge.homework.mapper.CardMapper;
import ge.homework.mapper.FoodMapper;
import ge.homework.model.entity.Order;
import ge.homework.model.entity.Payment;
import ge.homework.model.enums.OrderStatus;
import ge.homework.model.enums.PaymentStatus;
import ge.homework.model.request.OrderStatusUpdateReq;
import ge.homework.model.request.PaymentStatusUpdateReq;
import ge.homework.model.response.OrderResp;
import ge.homework.model.response.PaymentStatusResp;
import ge.homework.repository.OrderRepository;
import ge.homework.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final SessionService sessionService;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final FoodMapper foodMapper;
    private final CardMapper cardMapper;

    public PaymentStatusResp updatePaymentStatus(PaymentStatusUpdateReq req, String token) {
        sessionService.getAdminUser(token);

        Payment payment = paymentRepository.findById(req.getPaymentId())
            .orElseThrow(() -> new RuntimeException("no such payment"));
        
        if (req.getStatus() == PaymentStatus.PENDING) {
            throw new RuntimeException("payment must either succeed or fail");
        }
        Order order = payment.getOrder();
        
        if (order.getStatus() != OrderStatus.WAITING_FOR_PAYMENT_CONFIRM) {
            throw new RuntimeException("can't pay for this order");
        }
        
        payment.setStatus(req.getStatus());


        if (req.getStatus() == PaymentStatus.SUCCESS) {
            order.setStatus(OrderStatus.IN_PROGRESS);
        } else if (req.getStatus() == PaymentStatus.CANCELED) {
            order.setStatus(OrderStatus.READY_TO_PAY);
        }
        
        paymentRepository.save(payment);
        orderRepository.save(order);
        
        return cardMapper.toPaymentStatusResp(payment);
    }

    public OrderResp updateOrderStatus(OrderStatusUpdateReq req, String token) {
        sessionService.getAdminUser(token);
        Order order = orderRepository.findById(req.getOrderId())
            .orElseThrow(() -> new RuntimeException("order not found"));
        
        order.setStatus(req.getStatus());
        orderRepository.save(order);
        return foodMapper.toOrderResp(order);
    }

    public List<Long> fetchPendingPayments(String token) {
        sessionService.getAdminUser(token);
        return paymentRepository.findAllByStatus(PaymentStatus.PENDING)
            .stream().map(Payment::getId).toList();
    }

    public List<Long> fetchPendingOrders(String token) {
        sessionService.getAdminUser(token);
        return orderRepository.findAllByStatus(OrderStatus.IN_PROGRESS)
            .stream().map(Order::getId).toList();
    }
}
