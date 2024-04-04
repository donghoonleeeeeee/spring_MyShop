package portfolio1.Drink.Controller.Main;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import portfolio1.Drink.DTO.Users.UserDTO;
import portfolio1.Drink.Service.JoinService;


@Controller
@RequiredArgsConstructor
public class JoinController
{
    private final JoinService joinService;
    private final Logger LOGGER = LoggerFactory.getLogger(JoinController.class);

    @GetMapping("/join")
    public String join()
    {
        return "Join/join";
    }

    @PostMapping("/join_proc")
    public String join_proc(UserDTO userDTO)
    {
        joinService.join(userDTO);
        LOGGER.info("[Success Join] 가입을 축하합니다!");
        return "Login/login";
    }

    @PostMapping("/userid_check")
    @ResponseBody
    public boolean userid_check(@RequestParam("userid") String userid)
    {
        return joinService.userid_check(userid);
    }
}
