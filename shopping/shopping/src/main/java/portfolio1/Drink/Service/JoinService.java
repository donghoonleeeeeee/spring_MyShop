package portfolio1.Drink.Service;

import portfolio1.Drink.DTO.Users.UserDTO;


public interface JoinService
{
    public void join(UserDTO userDTO);

    boolean userid_check(String userid);
}
