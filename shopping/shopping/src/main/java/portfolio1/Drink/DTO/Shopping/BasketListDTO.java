package portfolio1.Drink.DTO.Shopping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BasketListDTO
{
    private Long Item_idx;
    private Long Basket_idx;
    private String thumbnail;
    private String ItemName;
    private int quantity;
    private String price;
}
