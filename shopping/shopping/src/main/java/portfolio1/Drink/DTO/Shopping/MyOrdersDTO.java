package portfolio1.Drink.DTO.Shopping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyOrdersDTO
{
    private Long idx;

    private String item_name;

    private String item_price;

    private int quantity;

    private String pay_type;

    private String regdate;

    private String year;

    private String month;

    private String day;
}
