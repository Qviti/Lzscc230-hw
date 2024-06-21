package ge.homework.model.response;

import ge.homework.model.enums.FoodType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FetchFoodsResp {
    private List<FoodData> data;

    @Data
    @Builder
    public static class FoodData {
        private Long id;
        private FoodType category;
        private String name;
        private Integer price;
    }
}
