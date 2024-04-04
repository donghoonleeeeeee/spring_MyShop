package portfolio1.Drink.Entity.Items;

import jakarta.persistence.*;
import lombok.*;
import portfolio1.Drink.DTO.Items.CategoryDTO;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="items_category")
public class ItemCategoryEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // 기본키

    @Setter
    @Column(name = "category")
    private String category;

    @Setter
    @Column(name="name")
    private String name;

    @OneToMany(mappedBy="categoryEntity", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<ItemsEntity> itemsEntities = new ArrayList<>();

    public CategoryDTO toDTO()
    {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCode(category);
        categoryDTO.setCategory_name(name);
        return categoryDTO;
    }
}
