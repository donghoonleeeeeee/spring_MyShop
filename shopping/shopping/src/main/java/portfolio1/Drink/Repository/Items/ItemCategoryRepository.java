package portfolio1.Drink.Repository.Items;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import portfolio1.Drink.Entity.Items.ItemCategoryEntity;

import java.util.List;

public interface ItemCategoryRepository extends JpaRepository<ItemCategoryEntity,Long>
{
    @Query(value = "select max(to_number(category)) from community_category",nativeQuery = true)
    String findByLabel();

    @Query("select c from ItemCategoryEntity c where LENGTH(c.category) = 2")
    List<ItemCategoryEntity> first_code();

    @Query("select c from ItemCategoryEntity c where LENGTH(c.category) = 4")
    List<ItemCategoryEntity> findByLabel2();

    List<ItemCategoryEntity> findAllByCategoryStartingWith(String Label);

    Page<ItemCategoryEntity> findAllByCategoryStartingWith(String Label, Pageable pageable);

    ItemCategoryEntity findByCategory(String category);

}
