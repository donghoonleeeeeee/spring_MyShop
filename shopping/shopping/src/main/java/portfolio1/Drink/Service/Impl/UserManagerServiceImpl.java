package portfolio1.Drink.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import portfolio1.Drink.DTO.Users.UserDTO;
import portfolio1.Drink.DTO.Users.UserListDTO;
import portfolio1.Drink.Entity.UserEntity;
import portfolio1.Drink.Repository.UserRepository;
import portfolio1.Drink.Service.UserManagerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserManagerServiceImpl implements UserManagerService
{
    private final UserRepository userRepository;

    @Override
    public String findUsername(String userid)
    {
        UserEntity user = userRepository.findByUserid(userid);
        return user.getName();
    }

    @Override
    public List<UserListDTO> UserList()
    {
        List<UserEntity> users_entity = userRepository.findAll();
        List<UserListDTO> users_dto = new ArrayList<>();
        int year = 0;
        String gender = null;

        for(int a=0; a<users_entity.size(); a++)
        {
            year = Integer.parseInt(users_entity.get(a).getRrn().split("-")[0].substring(0,2));

            if(year>25)
            {
                year = 1900+year;
            }
            else
            {
                year = 2000+year;
            }

            gender = users_entity.get(a).getRrn().split("-")[1].substring(0,1);

            if(gender.equals("1") || gender.equals("3") || gender.equals("5") || gender.equals("7"))
            {
                gender = "M";
            }
            else if(gender.equals("2") || gender.equals("4") || gender.equals("6") || gender.equals("8"))
            {
                gender = "F";
            }
            else
            {
                gender = "X";
            }

            UserListDTO userListDTO = UserListDTO.builder()
                    .idx(users_entity.get(a).getIdx())
                    .name(users_entity.get(a).getName())
                    .gender(gender)
                    .year(String.valueOf(year))
                    .month(users_entity.get(a).getRrn().split("-")[0].substring(2,4))
                    .day(users_entity.get(a).getRrn().split("-")[0].substring(4,6))
                    .hp(users_entity.get(a).getHp())
                    .build();

            users_dto.add(userListDTO);
        }
        return users_dto;
    }

    @Override
    public UserDTO UserView(Long idx)
    {
        UserEntity user = userRepository.findById(idx).orElse(null);
        UserDTO dto_user = user.toDTO();
        dto_user.setL_rrn(dto_user.getL_rrn().substring(0,1));

        return dto_user;
    }

    @Override
    public void UserModify(UserDTO userDTO)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(userDTO.toEntity());
    }

    @Override
    public void UserDelete(Long[] idx)
    {
        for(int a=0; a<idx.length; a++)
        {
            UserEntity users = userRepository.findById(idx[a]).orElse(null);
            userRepository.delete(users);
        }
    }
}
