package portfolio1.Drink.Security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class AuthenticationManagerImpl implements AuthenticationManager
{
    private final UserDetailsService userDetailsService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationManagerImpl.class);
    private final HttpServletResponse response;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        String userid = (String)authentication.getPrincipal(); // 입력된 아이디
        String password = (String)authentication.getCredentials(); // 입력된 비밀번호
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        LOGGER.info("[로그인 시도 중! / ID:{}, PASSWORD:{}]",userid,password);
        UserDetails user = userDetailsService.loadUserByUsername(userid);
        UsernamePasswordAuthenticationToken authenticationToken = null;

        if(user == null)
        {
            LOGGER.info("[일치하는 정보 없음!]");
            throw new BadCredentialsException("일치하는 정보가 없습니다!");
        }
        else
        {
            LOGGER.info("[Userid:{}, Password:{}, Grade:{}]", userid, password, user.getAuthorities());
            LOGGER.info("[DataBase / Userid:{}]", user.getUsername());
            if(!passwordEncoder.matches(password,user.getPassword()))
            {
                LOGGER.info("[비밀번호가 일치하지 않음!]");
                throw new BadCredentialsException("일치하는 정보가 없습니다!");
            }
            else
            {
                authenticationToken = new UsernamePasswordAuthenticationToken(userid, null, user.getAuthorities());
                Cookie cookie = new Cookie("login","login");
                cookie.setDomain("localhost");
                cookie.setPath("/");
                cookie.setMaxAge(60*60);
                cookie.setSecure(true);
                cookie.setHttpOnly(true);
                response.addCookie(cookie);

                return authenticationToken;
            }
        }


    }
}
