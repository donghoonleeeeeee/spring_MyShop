package portfolio1.Drink.Entity.Items;

import jakarta.persistence.*;
import lombok.*;
import portfolio1.Drink.Entity.Items.ItemsEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="items_file")
public class ItemsFileEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Setter
    @Column(name="origin")
    private String origin; // 파일 원본 이름

    @Setter
    @Column(name="path")
    private String path; // uuid + 파일 우너본 이름

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tidx")
    private ItemsEntity itemsEntity;

}
