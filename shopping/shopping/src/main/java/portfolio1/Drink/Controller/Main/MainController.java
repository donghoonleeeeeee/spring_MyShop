package portfolio1.Drink.Controller.Main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import portfolio1.Drink.DTO.Users.UserDTO;
import portfolio1.Drink.Entity.Items.ItemsEntity;
import portfolio1.Drink.Service.ItemsService;
import portfolio1.Drink.Service.JoinService;
import portfolio1.Drink.Service.ShoppingService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController
{
    private final JoinService joinService;
    private final ItemsService itemsService;
    private final ShoppingService shoppingService;
    private final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/home")
    public String home()
    {
        return "Login/home";
    }

    @GetMapping("/login")
    public String login()
    {
        return "Login/login";
    }

    @GetMapping("/logout")
    public String logout()
    {
        return "/logout";
    }

    @GetMapping("/login_fail")
    public String login_fail()
    {
        return "Login/fail";
    }

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

    @GetMapping("/ItemList")
    public String Item_List(Model model, HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(name="category", defaultValue="none")String category,
                            @RequestParam(name="keyword", defaultValue="none")String keyword,
                            @PageableDefault(page=0, size=12, sort = "idx", direction = Sort.Direction.DESC) Pageable pageable)
    {
        Page<ItemsEntity> list = itemsService.item_list(category, keyword, pageable);
        int scale = 2;
        int nowPage = list.getPageable().getPageNumber()+1;

        model.addAttribute("item_list",list);
        model.addAttribute("first",itemsService.first_View());
        model.addAttribute("category",category);
        model.addAttribute("keyword",keyword);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage", Math.max(nowPage - scale, 1));
        model.addAttribute("endPage", Math.min(nowPage + scale, list.getTotalPages()));
        model.addAttribute("maxPage", list.getTotalPages());

        return "Anonymous/ItemList";
    }

    @GetMapping("/ItemView")
    public String Item_View(@RequestParam("idx")Long idx, Model model)
    {
        model.addAttribute("tag",itemsService.itemCategories(idx));
        model.addAttribute("item", itemsService.Items_view(idx));
        model.addAttribute("images",itemsService.Item_Images(idx));
        model.addAttribute("likes",shoppingService.LikeCount());
        return "Anonymous/ItemView";
    }

    @PostMapping("/ItemLike")
    @ResponseBody
    public Boolean Item_likes(@RequestParam(name="idx",defaultValue="none")String idx, Principal principal)
    {
        return shoppingService.ItemLikes(Long.valueOf(idx), principal);
    }
}
