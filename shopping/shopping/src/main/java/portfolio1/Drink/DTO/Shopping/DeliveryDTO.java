package portfolio1.Drink.DTO.Shopping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import portfolio1.Drink.Entity.Shopping.DeliveryEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeliveryDTO
{
    private Long idx;
    private String getter; // 받는분
    private String hp; // 연락처
    private String add1; // 주소1
    private String add2; // 주소2
    private String add3; // 주소3
    private String request; // 요청사항
    private String regdate;

    public DeliveryEntity toEntity()
    {
        Date now = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return DeliveryEntity.builder()
                .name(getter)
                .hp(hp)
                .address(add1+"/"+add2+"/"+add3)
                .request(request)
                .regdate(sd.format(now))
                .build();
    }
}
