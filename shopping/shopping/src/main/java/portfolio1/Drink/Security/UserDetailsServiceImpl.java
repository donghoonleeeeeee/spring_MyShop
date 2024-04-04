package portfolio1.Drink.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import portfolio1.Drink.Entity.UserEntity;
import portfolio1.Drink.Repository.UserRepository;


@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService
{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserEntity user = userRepository.findByUserid(username);
        return user;
    }
}
