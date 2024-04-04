package portfolio1.Drink.Entity.Community;

import jakarta.persistence.*;
import lombok.*;
import portfolio1.Drink.DTO.Community.CommentDTO;
import portfolio1.Drink.Entity.UserEntity;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="community_comment")
public class CommentEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // 기본키

    @Setter
    @Column(name="userid")
    private String userid; // 작성자

    @Setter
    @Column(name="content")
    private String content; // 내용

    @Setter
    @Column(name="regdate")
    private String regdate; // 등록일

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_idx")
    private CommunityEntity communityEntity;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private UserEntity userEntity;

    public CommentDTO toDTO()
    {
        return CommentDTO.builder()
                .idx(idx)
                .userid(userid)
                .regdate(regdate)
                .content(content)
                .build();
    }
}
