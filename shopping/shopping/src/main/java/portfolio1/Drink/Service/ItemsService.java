package portfolio1.Drink.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import portfolio1.Drink.DTO.Items.CategoryDTO;
import portfolio1.Drink.DTO.Items.ItemsDTO;
import portfolio1.Drink.DTO.Items.ItemsModifyDTO;
import portfolio1.Drink.Entity.Items.ItemCategoryEntity;
import portfolio1.Drink.Entity.Items.ItemsEntity;

import java.util.List;

public interface ItemsService
{
    void input_items(ItemsDTO itemsDTO) throws Exception;
    Page<ItemsEntity> item_list(String category, String keyword, Pageable pageable);
    void items_modify(String[] check ,ItemsModifyDTO itemsModifyDTO);
    ItemsEntity Items_view(Long idx);
    void modify(List<String> check, ItemsDTO itemsDTO) throws Exception;
    void Items_List_Delete(List<Long> idx);
    //String Label_Check(String category, String info, Pageable pageable);
    String Label_Check(String category, String info);
    void Category_Register(CategoryDTO categoryDTO);
    List<CategoryDTO> first_View();
    List<String> SetLabel(String code);
    List<CategoryDTO> Category_All();
    List<ItemCategoryEntity> StartWithCategory(String code);
    void Category_modify(CategoryDTO categoryDTO);
    void Category_delete(CategoryDTO categoryDTO);
    List<String> Item_Images(Long idx);
}
