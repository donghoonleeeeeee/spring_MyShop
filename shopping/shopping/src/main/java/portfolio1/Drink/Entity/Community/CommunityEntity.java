package portfolio1.Drink.Entity.Community;


import jakarta.persistence.*;
import lombok.*;
import portfolio1.Drink.DTO.Community.CommunityDTO;
import portfolio1.Drink.DTO.Community.CommunityListDTO;
import portfolio1.Drink.DTO.Community.CommunityViewDTO;
import portfolio1.Drink.Entity.UserEntity;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="drink_community")
public class CommunityEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // 기본키

    @Setter
    @Column(name="type")
    private String type; // 공지사항 or 리뷰 구분

    @Setter
    @Column(name="title")
    private String title; // 커뮤니티 글 제목

    @Setter
    @Column(name="content")
    private String content; // 글 내용

    @Setter
    @Column(name="regdate")
    private String regdate; // 작성일

    @Setter
    @Column(name="hit")
    private int hit; // 조회수

    @OneToMany(mappedBy = "communityEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityFileEntity> communityFileEntities = new ArrayList<>();

    @OneToMany(mappedBy = "communityEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> commentEntities = new ArrayList<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uidx")
    private UserEntity userEntity;

    public CommunityDTO toDTO()
    {
        return CommunityDTO.builder()
                .idx(idx)
                .type(type)
                .title(title)
                .content(content)
                .regdate(regdate)
                .hit(hit)
                .build();
    }

    public CommunityListDTO toListDTO(Principal principal)
    {
        CommunityListDTO list = new CommunityListDTO();
        list.setIdx(idx);
        if(principal.getName().equals("admin"))
        {
            list.setType("공지");
        }
        else
        {
            list.setType("리뷰");
        }
        list.setTitle(title);
        list.setHit(hit);
        return list;
    }

    public CommunityViewDTO toViewDTO()
    {
        CommunityViewDTO view = new CommunityViewDTO();
        view.setIdx(idx);
        view.setName(userEntity.getName());
        view.setType(type);
        view.setTitle(title);
        view.setContent(content);
        view.setRegdate(regdate);
        view.setHit(hit);

        return view;
    }

}
