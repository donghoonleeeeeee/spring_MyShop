package portfolio1.Drink.Entity.Items;


import jakarta.persistence.*;
import lombok.*;
import portfolio1.Drink.DTO.Items.ItemsDTO;
import portfolio1.Drink.Entity.Shopping.ItemLikeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name="drink_items")
public class ItemsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // 기본키

    @Column(name = "item")
    private String item; // 제품명

    @Setter
    @Column(name = "inventory")
    private String inventory; // 재고

    @Setter
    @Column(name = "price")
    private String price; // 가격

    @Column(name = "content")
    private String content; // 제품설명
    
    @Setter
    @Column(name="regdate")
    private String regdate; // 등록일

    @OneToMany(mappedBy = "itemsEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ItemsFileEntity> itemsFileEntities = new ArrayList<>();

    @OneToMany(mappedBy = "itemsEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ItemLikeEntity> likeEntities = new ArrayList<>();

    @Setter
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "category_idx")
    private ItemCategoryEntity categoryEntity;

    public ItemsDTO toDTO()
    {
        return ItemsDTO.builder()
                .idx(idx)
                .item(item)
                .price(price)
                .inventory(inventory)
                .content(content)
                .regdate(regdate)
                .category1(categoryEntity.getName())
                .image1(itemsFileEntities.get(0).getPath())
                .image2(itemsFileEntities.get(1).getPath())
                .image3(itemsFileEntities.get(2).getPath())
                .build();
    }

    public void list_modify(String inventory, String price)
    {
        this.inventory = inventory;
        this.price = price;
    }

}
