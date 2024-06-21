package ge.homework.service;

import ge.homework.mapper.CardMapper;
import ge.homework.model.entity.Card;
import ge.homework.model.entity.User;
import ge.homework.model.request.AddCardToUserReq;
import ge.homework.model.response.AddCardToUserResp;
import ge.homework.repository.CardRepository;
import ge.homework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final SessionService sessionService;
    private final CardMapper cardMapper;
    private final UserRepository userRepository;

    public AddCardToUserResp addCardToUser(AddCardToUserReq req, String token) {
        sessionService.getAdminUser(token);
        User user = userRepository.findById(req.getUserId())
            .orElseThrow(() -> new RuntimeException("no such user"));    
        
        Card card = Card.builder()
            .cardNumber(req.getCardNumber())
            .cardSecurityCode(req.getCardSecurityCode())
            .user(user)
            .build();
        card = cardRepository.save(card);
        return cardMapper.toAddCardToUserResp(card);
    }
}