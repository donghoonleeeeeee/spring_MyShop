package portfolio1.Drink.Entity.Shopping;


import jakarta.persistence.*;
import lombok.*;
import portfolio1.Drink.DTO.Shopping.MyOrdersDTO;
import portfolio1.Drink.DTO.Shopping.OrderDTO;
import portfolio1.Drink.Entity.Items.ItemsEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name="drink_order")
public class OrderEntity
{
    @Id
    @GeneratedValue
    private Long idx;

    @Setter
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "item_idx")
    private ItemsEntity itemsEntity;

    @Setter
    @Column(name="quantity")
    private int quantity;

    @Setter
    @Column(name="pay_type")
    private String pay_type;

    @Setter
    @Column(name="total_pay")
    private int total_pay;

    @Setter
    @Column(name="userid")
    private String userid;

    @Setter
    @Column(name="regdate")
    private String regdate;


    public MyOrdersDTO toDTO()
    {
        return MyOrdersDTO.builder()
                .idx(idx)
                .item_name(itemsEntity.getItem())
                .item_price(itemsEntity.getPrice())
                .quantity(quantity)
                .pay_type(pay_type)
                .regdate(regdate)
                .build();
    }

}
