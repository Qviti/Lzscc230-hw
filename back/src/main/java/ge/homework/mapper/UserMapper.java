package ge.homework.mapper;

import ge.homework.model.entity.Food;
import ge.homework.model.entity.User;
import ge.homework.model.request.AddFoodReq;
import ge.homework.model.response.FoodResp;
import ge.homework.model.response.RegisterUserResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    RegisterUserResp toRegisterUserResp(User user);

    @Mapping(target = "id", ignore = true)
    Food toFoodEntity(AddFoodReq req);

    FoodResp toFoodResponse(Food food);
}
