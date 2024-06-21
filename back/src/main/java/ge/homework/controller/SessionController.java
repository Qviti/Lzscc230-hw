package ge.homework.controller;

import ge.homework.model.request.AddFoodReq;
import ge.homework.model.request.UserCredentialsReq;
import ge.homework.model.response.FoodResp;
import ge.homework.model.response.RegisterUserResp;
import ge.homework.service.SessionService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sm")
public class SessionController {
    private final SessionService sessionService;

    @PostMapping("/sessions")
    public RegisterUserResp createSession(@RequestBody UserCredentialsReq req, HttpServletResponse response) {
        return sessionService.createSession(req, response);
    }

    @GetMapping("/sessions")
    public RegisterUserResp getSession(@RequestHeader("Auth") String token) {
        return sessionService.getSession(token);
    }

    @PostMapping("/food")
    public FoodResp addFood(@RequestBody AddFoodReq req, @RequestHeader("Auth") String token) {
        return sessionService.addFood(req, token);
    }
}

