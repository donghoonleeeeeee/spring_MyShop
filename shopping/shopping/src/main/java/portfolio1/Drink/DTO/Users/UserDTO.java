package portfolio1.Drink.DTO.Users;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import portfolio1.Drink.Entity.UserEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO
{
    private Long idx; // 기본키
    private int grade; // 권한 (1.Admin 2.User 3.Guest)
    private String userid; // 아이디
    private String password; // 암호
    private String name; // 이름
    private String f_rrn; // 주민번호 앞자리
    private String l_rrn; // 주민번호 뒷자리
    private String hp; // 연락처
    private String add1; // 주소
    private String add2;
    private String add3;
    private String mail; // 메일
    private String web; // 메일 웹 주소
    private String regdate; // 가입일
    private int cash;


    public UserEntity toEntity()
    {
        return UserEntity.builder()
                .idx(idx)
                .grade(grade) // <- 가입 시 기본 셋팅 2(User)
                .userid(userid)
                .password(password)
                .name(name)
                .rrn(f_rrn + "-" + l_rrn + "******")
                .hp(hp)
                .address(add1+"/"+add2+"/"+add3)
                .email(mail+web)
                .regdate(regdate)
                .cash(cash)
                .build();
    }

}
