package portfolio1.Drink.DTO.Shopping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentDTO
{
    private String[] basket_idx; // 장바구니 idx
    private String[] item_idx; // 아이템 idx
    private String[] item_quantity; // 수량
}
