package portfolio1.Drink.Entity.Shopping;

import jakarta.persistence.*;
import lombok.*;
import portfolio1.Drink.DTO.Shopping.BasketDTO;
import portfolio1.Drink.Entity.Items.ItemsEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name="drink_basket")
public class BasketEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Setter
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "item_idx")
    private ItemsEntity itemsEntity;

    @Setter
    @Column(name="quantity")
    private int quantity;

    @Setter
    @Column(name="userid")
    private String userid;

    public BasketDTO toDTO()
    {
        return BasketDTO.builder()
                .idx(idx)
                .quantity(quantity)
                .userid(userid)
                .build();
    }
}
