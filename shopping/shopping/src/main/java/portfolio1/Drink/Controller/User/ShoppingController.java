package portfolio1.Drink.Controller.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import portfolio1.Drink.Entity.Items.ItemsEntity;
import portfolio1.Drink.Service.ItemsService;

import java.util.List;

@Controller
@RequestMapping("/User")
@RequiredArgsConstructor
public class ShoppingController
{
    private final ItemsService itemsService;

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

        return "User/Items/ItemList";
    }

    @PostMapping("/SetLabel")
    @ResponseBody
    public List<String> Category_Setting(@RequestParam("code")String code)
    {
        return itemsService.SetLabel(code);
    }

    @GetMapping("/ItemView")
    public String Item_View(@RequestParam("idx")Long idx, Model model)
    {
        model.addAttribute("tag",itemsService.itemCategories(idx));
        model.addAttribute("item", itemsService.Items_view(idx));
        model.addAttribute("images",itemsService.Item_Images(idx));
        return "User/Items/ItemView";
    }
}
