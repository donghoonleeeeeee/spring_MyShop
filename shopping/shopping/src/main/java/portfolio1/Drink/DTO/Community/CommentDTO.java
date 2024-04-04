package portfolio1.Drink.DTO.Community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import portfolio1.Drink.Entity.Community.CommentEntity;
import portfolio1.Drink.Entity.Community.CommunityEntity;
import portfolio1.Drink.Entity.UserEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO
{
    private Long idx;
    private String content;
    private String regdate;
    private String userid;

    public CommentEntity toEntity(CommunityEntity communityEntity, UserEntity userEntity)
    {
        Date now = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(content);
        commentEntity.setRegdate(sd.format(now));
        commentEntity.setUserid(userEntity.getUserid());
        commentEntity.setCommunityEntity(communityEntity);
        commentEntity.setUserEntity(userEntity);
        return commentEntity;
    }
}
