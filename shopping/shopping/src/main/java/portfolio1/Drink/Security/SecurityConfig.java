package portfolio1.Drink.Security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig
{
    private final AuthenticationManager authenticationManager;

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/main/**","/image/**","/js/**","/css/**","/tools/**").permitAll()
                .requestMatchers("/admin/**").hasRole("3")
                .requestMatchers("/User/**","/community/**").hasAnyRole("3","2")
                .anyRequest().authenticated()
        );

        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);

        http
                .formLogin(Login -> Login
                        .loginPage("/main/login")
                        .loginProcessingUrl("/login_proc")
                        .defaultSuccessUrl("/main/home",true)
                        .failureUrl("/main/login_fail")
                        .usernameParameter("userid")
                        .passwordParameter("password")
                        .permitAll()
                );

        http
                .logout(Logout -> Logout
                        .logoutUrl("/main/logout")
                        .deleteCookies("login")
                        .logoutSuccessUrl("/main/login")
                        .permitAll()
                );
        /*
            defaultSuccessUrl을 주석처리하고 login_proc에서 grade에 따라 respose.sendredirect로 각 각의 홈 보내기
        */

        http.authenticationManager(authenticationManager);
        return http.build();
    }
}
