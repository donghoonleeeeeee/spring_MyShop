package portfolio1.Drink.Controller.Admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import portfolio1.Drink.DTO.Users.UserDTO;
import portfolio1.Drink.Service.CommunityService;
import portfolio1.Drink.Service.UserManagerService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserManagerController
{
    private final UserManagerService userManagerService;
    private final CommunityService communityService;

    @GetMapping("/UserList")
    public String User_List(Model model)
    {
        model.addAttribute("user_list",userManagerService.UserList());
        return "Admin/UserList";
    }

    @GetMapping("/UserView")
    public String User_View(Model model, @RequestParam("idx")Long idx)
    {
        model.addAttribute("user",userManagerService.UserView(idx));
        return "Admin/UserView";
    }

    @PostMapping("/UserModify")
    public String User_Modify(Model model, @RequestParam("idx")Long idx)
    {
        model.addAttribute("user",userManagerService.UserView(idx));
        return "Admin/UserModify";
    }

    @PostMapping("/UserModify_proc")
    public String User_Modify_proc(UserDTO userDTO)
    {
        userManagerService.UserModify(userDTO);
        return "redirect:/admin/UserView?idx="+userDTO.getIdx();
    }

    @PostMapping("/Users_Delete")
    public String Users_Delete(@RequestParam("User_Check")Long[] idx)
    {
        userManagerService.UserDelete(idx);
        return "redirect:/admin/UserList";
    }
}
