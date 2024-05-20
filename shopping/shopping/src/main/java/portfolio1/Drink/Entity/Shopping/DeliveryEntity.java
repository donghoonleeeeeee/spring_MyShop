package portfolio1.Drink.Entity.Shopping;


import jakarta.persistence.*;
import lombok.*;
import portfolio1.Drink.DTO.Shopping.DeliveryDTO;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name="drink_delivery")
public class DeliveryEntity
{
    @Id
    @GeneratedValue
    private Long idx;

    @Setter
    @Column(name="userid")
    private String userid;

    @Setter
    @Column(name="name") // 받는분
    private String name;

    @Setter
    @Column(name="hp") // 연락처
    private String hp;

    @Setter
    @Column(name="address") // 주소
    private String address;

    @Setter
    @Column(name="request") // 배송 시 요청사항
    private String request;

    @Setter
    @Column(name="regdate") // 주문일
    private String regdate;

    public DeliveryDTO toDTO()
    {
        return DeliveryDTO.builder()
                .idx(idx)
                .getter(name)
                .add1(address.split("/")[0])
                .add2(address.split("/")[1])
                .add3(address.split("/")[2])
                .hp(hp)
                .request(request)
                .regdate(regdate)
                .build();
    }

}
