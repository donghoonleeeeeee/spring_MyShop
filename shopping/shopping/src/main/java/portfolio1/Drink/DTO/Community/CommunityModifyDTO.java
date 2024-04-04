package portfolio1.Drink.DTO.Community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import portfolio1.Drink.Entity.Community.CommunityEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityModifyDTO
{
    private Long idx;
    private String type;
    private String title;
    private String content;
    private String regdate;
    private int hit;
    private List<MultipartFile> files;

    public CommunityEntity toEntity()
    {
        return CommunityEntity.builder()
                .idx(idx)
                .type(type)
                .title(title)
                .content(content)
                .regdate(regdate)
                .hit(hit)
                .build();
    }
}
