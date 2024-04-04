package portfolio1.Drink.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import portfolio1.Drink.DTO.Users.UserDTO;
import portfolio1.Drink.Entity.UserEntity;
import portfolio1.Drink.Repository.UserRepository;
import portfolio1.Drink.Service.JoinService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService
{
    private final Logger LOGGER = LoggerFactory.getLogger(JoinServiceImpl.class);
    private final UserRepository userRepository;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date now = new Date();

    public void join(UserDTO userDTO)
    {
        userDTO.setCash(500000); // 가입 시 예치금 50만원 설정
        userDTO.setGrade(2); // 가입시 권한 레벨2(User)
        userDTO.setRegdate(dateFormat.format(now)); // 오늘 날짜 세팅

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        
        UserEntity userEntity = userDTO.toEntity();
        LOGGER.info("[Join] 회원가입을 수행합니다. / ID:{}, Password:****, name:{}, Role:{}", userDTO.getUserid(), userDTO.getName(), userDTO.getGrade());
        userRepository.save(userEntity);
    }

    public boolean userid_check(String userid)
    {
        return userRepository.existsByUserid(userid);
    }


}
