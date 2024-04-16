package portfolio1.Drink.Entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import portfolio1.Drink.DTO.Users.UserDTO;
import portfolio1.Drink.Entity.Community.CommentEntity;
import portfolio1.Drink.Entity.Community.CommunityEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="drink_member")
public class UserEntity implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // 기본키

    @Setter
    @Column(name="grade")
    private int grade;

    @Column(name="userid", unique = true) // 아이디
    private String userid;

    @Column(name="password") // 암호
    private String password;

    @Setter
    @Column(name="name") // 이름
    private String name;

    @Column(name="rrn") // 주민번호
    private String rrn;

    @Setter
    @Column(name="hp") // 연락처
    private String hp;

    @Setter
    @Column(name="address") // 주소
    private String address;

    @Setter
    @Column(name="email") // 이메일
    private String email;

    @Column(name="regdate") // 가입일
    private String regdate;

    @Setter
    @Column(name="cash") // 잔액
    private int cash;

    @OneToMany(mappedBy="userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommunityEntity> communityEntities = new ArrayList<>();

    @OneToMany(mappedBy="userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentEntity> commentEntities = new ArrayList<>();

    public UserDTO toDTO()
    {
        return UserDTO.builder()
                .idx(idx)
                .grade(grade)
                .userid(userid)
                .password(password)
                .name(name)
                .f_rrn(rrn.split("-")[0])
                .l_rrn(rrn.split("-")[1])
                .hp(hp)
                .add1(address.split("/")[0])
                .add2(address.split("/")[1])
                .add3(address.split("/")[2])
                .mail(email.split("@")[0])
                .web("@"+email.split("@")[1])
                .regdate(regdate)
                .cash(cash)
                .build();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+grade));

        return authorities;
    }

    @Override
    public String getUsername()
    {
        return userid;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}

