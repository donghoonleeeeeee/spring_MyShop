package portfolio1.Drink.DTO.Community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityKeyDTO
{
    private Long user_idx; // UserEntity
    private Long community_idx; // CommentEntity
    private Long comment_idx;
}
