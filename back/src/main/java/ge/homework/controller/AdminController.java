package ge.homework.controller;

import ge.homework.model.request.OrderStatusUpdateReq;
import ge.homework.model.request.PaymentStatusUpdateReq;
import ge.homework.model.response.OrderResp;
import ge.homework.model.response.PaymentStatusResp;
import ge.homework.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    
    private final AdminService adminService;
    
    @PostMapping("/update/payment/status")
    public PaymentStatusResp updatePaymentStatus(@RequestBody PaymentStatusUpdateReq req, @RequestHeader("Auth") String token) {
        return adminService.updatePaymentStatus(req, token);
    }
    
    @GetMapping("/payments")
    public List<Long> fetchPendingPayments(@RequestHeader("Auth") String token) {
        return adminService.fetchPendingPayments(token);
    }
    
    @GetMapping("/orders")
    public List<Long> fetchPendingOrders(@RequestHeader("Auth") String token) {
        return adminService.fetchPendingOrders(token);
    }
    
    @PostMapping("/update/order/status")
    public OrderResp updateOrderStatus(@RequestBody OrderStatusUpdateReq req, @RequestHeader("Auth") String token) {
        return adminService.updateOrderStatus(req, token);
    }
}
