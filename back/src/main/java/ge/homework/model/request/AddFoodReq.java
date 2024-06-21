package ge.homework.model.request;


import ge.homework.model.enums.FoodType;
import lombok.Data;

@Data
public class AddFoodReq {
    private FoodType category;

    private int price;

    private String name;
}
