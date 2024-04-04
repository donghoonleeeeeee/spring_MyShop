package portfolio1.Drink.DTO.Items;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ItemsModifyDTO
{
    private String[] inventory;
    private String[] price;
}
