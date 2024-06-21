package ge.homework.controller;

import ge.homework.model.request.AddCardToUserReq;
import ge.homework.model.response.AddCardToUserResp;
import ge.homework.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    @PostMapping
    public AddCardToUserResp addCardToUser(@RequestBody AddCardToUserReq req, @RequestHeader("Auth") String token) {
        return cardService.addCardToUser(req, token);
    }
}