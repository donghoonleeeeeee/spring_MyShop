package portfolio1.Drink.Entity.Community;

import jakarta.persistence.*;
import lombok.*;
import portfolio1.Drink.Entity.Community.CommunityEntity;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="community_file")
public class CommunityFileEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Setter
    @Column(name="origin")
    private String origin;

    @Setter
    @Column(name="path")
    private String path;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cidx")
    private CommunityEntity communityEntity;
}
