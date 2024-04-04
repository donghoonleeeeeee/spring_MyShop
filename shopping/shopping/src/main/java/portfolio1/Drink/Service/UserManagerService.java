package portfolio1.Drink.Service;

import portfolio1.Drink.DTO.Users.UserDTO;
import portfolio1.Drink.DTO.Users.UserListDTO;
import portfolio1.Drink.Entity.UserEntity;

import java.util.List;

public interface UserManagerService
{
    String findUsername(String userid);
    List<UserListDTO> UserList();

    UserDTO UserView(Long idx);

    void UserModify(UserDTO userDTO);

    void UserDelete(Long[] idx);
}
