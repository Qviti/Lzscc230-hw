package ge.homework.controller;

import ge.homework.model.request.AddFoodReq;
import ge.homework.model.response.AddFoodResp;
import ge.homework.model.response.FetchFoodsResp;
import ge.homework.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/foods")
public class FoodController {

    private final FoodService foodService;

    @PostMapping("/add")
    public AddFoodResp addFood(@RequestHeader("Auth") String token, @RequestBody AddFoodReq req) {
        return foodService.addFood(token, req);
    }

    @GetMapping
    public List<FetchFoodsResp.FoodData> fetchFoods() {
        return foodService.fetchFoods();
    }
}
