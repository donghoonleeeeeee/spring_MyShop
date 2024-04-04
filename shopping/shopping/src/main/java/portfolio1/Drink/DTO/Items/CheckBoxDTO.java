package portfolio1.Drink.DTO.Items;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckBoxDTO
{
        private String image_checked1;
        private String image_checked2;
        private String image_checked3;
}
