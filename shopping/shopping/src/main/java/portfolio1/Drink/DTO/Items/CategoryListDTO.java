package portfolio1.Drink.DTO.Items;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryListDTO
{
    private String first;
    private List<String> second;
    private List<String> third;
}
