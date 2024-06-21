package ge.homework.service;

import ge.homework.mapper.FoodMapper;
import ge.homework.model.entity.Food;
import ge.homework.model.request.AddFoodReq;
import ge.homework.model.response.AddFoodResp;
import ge.homework.model.response.FetchFoodsResp;
import ge.homework.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;
    private final UserService userService;
    private final SessionService sessionService;

    public AddFoodResp addFood(String token, AddFoodReq req) {
        sessionService.getAdminUser(token);
        Food food = Food.builder()
                .category(req.getCategory())
                .name(req.getName())
                .price(req.getPrice())
                .build();
        food = foodRepository.save(food);
        return foodMapper.toAddFoodResp(food);
    }

    public List<FetchFoodsResp.FoodData> fetchFoods() {
        return foodRepository.findAll().stream().map(foodMapper::toFoodData).toList();
    }
}

