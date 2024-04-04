package portfolio1.Drink.DTO.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserListDTO
{
    private Long idx;
    private String name;
    private String gender;
    private String year;
    private String month;
    private String day;
    private String hp;
}
