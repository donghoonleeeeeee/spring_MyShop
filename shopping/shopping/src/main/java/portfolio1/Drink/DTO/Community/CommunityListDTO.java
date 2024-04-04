package portfolio1.Drink.DTO.Community;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityListDTO
{
    private Long idx;
    private Long uidx;
    private String name;
    private String userid;
    private String type;
    private String title;
    private int hit;
    private int size;
}
