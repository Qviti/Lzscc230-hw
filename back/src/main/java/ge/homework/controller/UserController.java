package ge.homework.controller;

import ge.homework.model.request.RegisterUserReq;
import ge.homework.model.request.VerifyUserReq;
import ge.homework.model.response.RegisterUserResp;
import ge.homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/um")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public RegisterUserResp registerUser(@RequestBody RegisterUserReq req) {
        return userService.registerUser(req);
    }

    @PostMapping("/users/{id}/verification")
    public RegisterUserResp verifyUser(@RequestBody VerifyUserReq req, @PathVariable long id) {
        return userService.verifyUser(req, id);
    }
}