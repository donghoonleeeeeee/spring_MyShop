package portfolio1.Drink.DTO.Items;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import portfolio1.Drink.Entity.Items.ItemCategoryEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO
{
    private String code;
    private String category_name;

    private String modify_code;
    private String modify_name;

    public ItemCategoryEntity toLabel(String category, String name)
    {
        ItemCategoryEntity item_category = new ItemCategoryEntity();
        item_category.setCategory(category);
        item_category.setName(name);
        return item_category;
    }

}
