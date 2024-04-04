package portfolio1.Drink.DTO.Community;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import portfolio1.Drink.Entity.Community.CommunityEntity;
import portfolio1.Drink.Entity.Community.CommunityFileEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityDTO
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
        Date now = new Date();
        SimpleDateFormat sd = new SimpleDateFormat();
        return CommunityEntity.builder()
                .idx(idx)
                .type(type)
                .title(title)
                .content(content)
                .type(type)
                .regdate(sd.format(now))
                .hit(0)
                .build();
    }

    public CommunityFileEntity toFileEntity(String origin, String path, CommunityEntity communityEntity)
    {
        CommunityFileEntity communityFileEntity = new CommunityFileEntity();
        communityFileEntity.setOrigin(origin);
        communityFileEntity.setPath(path);
        communityFileEntity.setCommunityEntity(communityEntity);
        return communityFileEntity;
    }
}
