package portfolio1.Drink.DTO.Community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityViewDTO
{
    private Long idx;
    private String name;
    private String type;
    private String title;
    private String content;
    private String regdate;
    private int hit;
    private List<String> origin;
    private List<String> path;
}
