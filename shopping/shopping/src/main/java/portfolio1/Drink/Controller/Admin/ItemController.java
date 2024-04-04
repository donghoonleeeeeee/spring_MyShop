package portfolio1.Drink.Controller.Admin;

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
import portfolio1.Drink.DTO.Items.CategoryDTO;
import portfolio1.Drink.DTO.Items.ItemsDTO;
import portfolio1.Drink.DTO.Items.ItemsModifyDTO;
import portfolio1.Drink.Entity.Items.ItemsEntity;
import portfolio1.Drink.Service.ItemsService;
import portfolio1.Drink.Service.ToolsService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ItemController
{
    private final ItemsService itemsService;
    private final ToolsService toolsService;

    @GetMapping("/ItemType")
    public String item_type()
    {
        return "Admin/Items/ItemType";
    }

    @GetMapping("/ItemRegister")
    public String item_register(Model model)
    {
        model.addAttribute("first",itemsService.first_View());
        return "Admin/Items/ItemRegister";
    }

    @PostMapping("/ItemRegister_proc")
    public String item_register_proc(ItemsDTO itemsDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        itemsService.input_items(itemsDTO); // DB에 등록
        return "redirect:/admin/ItemType";
    }

    @GetMapping("/ItemList")
    public String Item_List(Model model, HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(name="category", defaultValue="none")String category,
                            @RequestParam(name="keyword", defaultValue="none")String keyword,
                            @PageableDefault(page=0, size=5, sort = "idx", direction = Sort.Direction.DESC) Pageable pageable)
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

        return "Admin/Items/ItemList";
    }

    @PostMapping("/List_modify")
    public String List_modify(@RequestParam(name="item_check", defaultValue="0")String[] check, ItemsModifyDTO itemsModifyDTO)
    {
        itemsService.items_modify(check, itemsModifyDTO);
        return "redirect:/admin/ItemList";
    }

    @GetMapping("/ItemsModify")
    public String Items_modify(@RequestParam("idx")Long idx, Model model)
    {
        model.addAttribute("first",itemsService.first_View());
        model.addAttribute("item",itemsService.Items_view(idx));
        model.addAttribute("category",itemsService.StartWithCategory(itemsService.itemCategory(idx)));
        model.addAttribute("code",itemsService.itemCategory(idx));

        return "Admin/Items/ItemsModify";
    }

    @PostMapping("/ItemsModify_proc")
    public String Items_modify_proc(@RequestParam(name="check",defaultValue="unchecked") List<String> check, ItemsDTO itemsDTO) throws Exception
    {
        itemsService.modify(check, itemsDTO);
        return "redirect:/admin/ItemsModify?idx="+itemsDTO.getIdx();
    }

    @PostMapping("/List_delete")
    public String List_deleter(@RequestParam("check")List<Long> check)
    {
        itemsService.Items_List_Delete(check);
        return "redirect:/admin/ItemList";
    }








}
