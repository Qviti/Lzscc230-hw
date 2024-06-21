package ge.homework.controller;

import ge.homework.model.request.PaymentReq;
import ge.homework.model.response.OrderResp;
import ge.homework.model.response.PaymentStatusResp;
import ge.homework.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    
    @PostMapping("/place")
    public void placeOrder(@RequestHeader("Auth") String token) {
        orderService.placeOrder(token);
    }
    
    @GetMapping("/fetch")
    public OrderResp fetchOrder(@RequestParam long orderId, @RequestHeader("Auth") String token) {
        return orderService.fetchOrder(orderId, token);
    }

    @GetMapping("/fetch/all")
    public List<OrderResp> fetchOrders(@RequestHeader("Auth") String token) {
        return orderService.fetchOrders(token);
    }
    
    @PostMapping("/pay")
    public PaymentStatusResp payOrder(@RequestBody PaymentReq req, @RequestHeader("Auth") String token) {
        return orderService.pay(req, token);
    }
}

