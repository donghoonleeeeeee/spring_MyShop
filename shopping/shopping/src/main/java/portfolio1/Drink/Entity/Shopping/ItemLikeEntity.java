package portfolio1.Drink.Entity.Shopping;

import jakarta.persistence.*;
import lombok.*;
import portfolio1.Drink.Entity.Items.ItemsEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "item_likes")
public class ItemLikeEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // 기본키

    @Setter
    @Column(name = "userid")
    private String userid;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_idx")
    private ItemsEntity itemsEntity;

    @Setter
    @Column
    private String regdate;
}
