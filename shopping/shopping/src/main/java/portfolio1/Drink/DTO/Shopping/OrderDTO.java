package portfolio1.Drink.DTO.Shopping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDTO
{
    private List<String> item_idx;
    private List<Integer> item_quantity;
    private List<String> basket_idx;
    private List<Integer> total_pay;
    private String pay; // 결제 타입

}
