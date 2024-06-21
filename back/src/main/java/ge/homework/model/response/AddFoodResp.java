package ge.homework.model.response;

import ge.homework.model.enums.FoodType;
import lombok.Data;

@Data
public class AddFoodResp {
    private Long id;
    private FoodType category;
    private String name;
    private Integer price;
}

