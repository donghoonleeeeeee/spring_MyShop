package portfolio1.Drink.Controller.Admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import portfolio1.Drink.DTO.Items.CategoryDTO;
import portfolio1.Drink.Service.ItemsService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class CategoryController
{
    private final ItemsService itemsService;

    @GetMapping("/ItemCategory")
    public String Items_Category(Model model)
    {
        model.addAttribute("first",itemsService.first_View());
        //model.addAttribute("View",itemsService.Category_List());
        return "Admin/Items/ItemCategory";
    }

    @PostMapping("/CategoryRegister")
    public String Category_Register(CategoryDTO categoryDTO)
    {
        itemsService.Category_Register(categoryDTO);
        return "redirect:/admin/ItemCategory";
    }

    @PostMapping("/LabelCheck")
    @ResponseBody
    public String Category_Label_Check(@RequestParam("category")String category, @RequestParam(name="info",defaultValue="none")String info)
    {
        return itemsService.Label_Check(category, info);
    }

    @PostMapping("/SetLabel")
    @ResponseBody
    public List<String> Category_Setting(@RequestParam("code")String code)
    {
        return itemsService.SetLabel(code);
    }


    @PostMapping("/Category_modify")
    public String Category_modify(CategoryDTO categoryDTO)
    {
        itemsService.Category_modify(categoryDTO);
        return "redirect:/admin/ItemCategory";
    }

    @PostMapping("/Category_delete")
    public String Category_delete(CategoryDTO categoryDTO)
    {
        itemsService.Category_delete(categoryDTO);
        return "redirect:/admin/ItemCategory";
    }
}
