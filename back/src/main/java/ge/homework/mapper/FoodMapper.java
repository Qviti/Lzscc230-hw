package ge.homework.mapper;

import ge.homework.model.entity.*;
import ge.homework.model.response.AddFoodResp;
import ge.homework.model.response.FetchFoodsResp;
import ge.homework.model.response.OrderItemResp;
import ge.homework.model.response.OrderResp;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FoodMapper {
    AddFoodResp toAddFoodResp(Food food);

    FetchFoodsResp.FoodData toFoodData(Food food);

    default FetchFoodsResp toFetchFoodsResp(List<Food> foods) {
        List<FetchFoodsResp.FoodData> foodDataList = foods.stream()
                .map(this::toFoodData)
                .toList();
        return FetchFoodsResp.builder().data(foodDataList).build();
    }

    OrderResp toOrderResp(Order order);
    
    OrderItemResp toOrderItemResp(OrderItem item);

    List<OrderResp> toOrderResp(List<Order> orders);
}

