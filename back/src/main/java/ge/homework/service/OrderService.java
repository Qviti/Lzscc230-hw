package ge.homework.service;

import ge.homework.mapper.CardMapper;
import ge.homework.mapper.FoodMapper;
import ge.homework.model.entity.*;
import ge.homework.model.enums.OrderStatus;
import ge.homework.model.enums.PaymentStatus;
import ge.homework.model.request.PaymentReq;
import ge.homework.model.response.OrderResp;
import ge.homework.model.response.PaymentStatusResp;
import ge.homework.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final SessionService sessionService;
    private final CartItemRepository cartItemRepository;
    private final FoodMapper foodMapper;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CardRepository cardRepository;
    private final PaymentRepository paymentRepository;
    private final CardMapper cardMapper;

    @Transactional
    public void placeOrder(String token) {
        User user = sessionService.getVerifiedUser(token);
        Cart cart = user.getCart();
        List<CartItem> items = cartItemRepository.findAllByCartOrderById(cart);

        Order order = new Order();

        List<OrderItem> orderItems = new ArrayList<>(items.size());
        for (CartItem item : items) {
            order.updateTotal(item.getTotal());
            order.updateItemCount(item.getQuantity());
            orderItems.add(
                OrderItem.builder()
                    .total(item.getTotal())
                    .quantity(item.getQuantity())
                    .food(item.getFood())
                    .price(item.getFood().getPrice())
                    .order(order)
                    .build()
            );
        }
        
        order.setItems(orderItems);
        order.setStatus(OrderStatus.READY_TO_PAY);
        order.setUser(user);
        
        cartItemRepository.removeByCart(cart);
        
        orderRepository.save(order);
    }

    public OrderResp fetchOrder(long orderId, String token) {
        User user = sessionService.getVerifiedUser(token);
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("no such order"));
        
        if (!Objects.equals(order.getUser().getId(), user.getId())) {
            throw new RuntimeException("this order does not belong to user");
        }

        return foodMapper.toOrderResp(order);
    }

    public PaymentStatusResp pay(PaymentReq req, String token) {
        User user = sessionService.getVerifiedUser(token);
        Order order = orderRepository.findById(req.getOrderId())
            .orElseThrow(() -> new RuntimeException("no such order"));

        if (!Objects.equals(order.getUser().getId(), user.getId())) {
            throw new RuntimeException("this order does not belong to user");
        }
        
        if (order.getStatus() != OrderStatus.READY_TO_PAY) {
            throw new RuntimeException("not accepting payments for this order");
        }

        Card card = cardRepository.findByUserAndCardNumberAndCardSecurityCode(user, req.getCardNumber(), req.getSecurityCode())
            .orElseThrow(() -> new RuntimeException("card does not belong to user"));

        Payment payment = Payment.builder()
            .order(order)
            .card(card)
            .amount(order.getTotal())
            .status(PaymentStatus.PENDING)
            .build();
        
        payment = paymentRepository.save(payment);        
        order.setStatus(OrderStatus.WAITING_FOR_PAYMENT_CONFIRM);
        orderRepository.save(order);
        return cardMapper.toPaymentStatusResp(payment);
    }

    public List<OrderResp> fetchOrders(String token) {
        User user = sessionService.getVerifiedUser(token);
        List<Order> orders = orderRepository.findAllByUser(user);
        
        return foodMapper.toOrderResp(orders);
    }
}
