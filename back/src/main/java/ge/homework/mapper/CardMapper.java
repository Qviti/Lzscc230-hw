package ge.homework.mapper;

import ge.homework.model.entity.Card;
import ge.homework.model.entity.Payment;
import ge.homework.model.entity.User;
import ge.homework.model.response.AddCardToUserResp;
import ge.homework.model.response.PaymentStatusResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "card.id", target = "id")
    AddCardToUserResp toAddCardToUserResp(Card card, User user);

    @Mapping(target = "userId", expression = "java(card.getUser().getId())")
    AddCardToUserResp toAddCardToUserResp(Card card);

    PaymentStatusResp toPaymentStatusResp(Payment payment);
}