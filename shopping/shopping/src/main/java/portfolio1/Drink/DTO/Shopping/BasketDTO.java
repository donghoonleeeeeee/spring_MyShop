package portfolio1.Drink.DTO.Shopping;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import portfolio1.Drink.Entity.Items.ItemsEntity;
import portfolio1.Drink.Entity.Shopping.BasketEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketDTO
{
    private Long idx;
    private Long item_idx;
    private int quantity;
    private String userid;

    public BasketEntity toEntity()
    {
        return BasketEntity.builder()
                .idx(idx)
                .quantity(quantity)
                .userid(userid)
                .build();
    }
}
