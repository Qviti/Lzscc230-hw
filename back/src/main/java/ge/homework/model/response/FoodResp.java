package ge.homework.model.response;

import ge.homework.model.enums.FoodType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodResp {
    private FoodType category;

    private long price;

    private String name;
    
    private Long id;
}
