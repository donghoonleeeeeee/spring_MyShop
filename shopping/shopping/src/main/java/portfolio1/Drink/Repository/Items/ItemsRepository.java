package portfolio1.Drink.Repository.Items;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import portfolio1.Drink.Entity.Items.ItemsEntity;

import java.util.List;

public interface ItemsRepository extends JpaRepository<ItemsEntity,Long>
{
    //Page<ItemsEntity> findAllByCategory1(Pageable pageable, String category1);
    //Page<ItemsEntity> findAllByCategory1AndCategory2(Pageable pageable, String category1, String category2);
    //Page<ItemsEntity> findAllByCategory1AndCategory2AndCategory3(Pageable pageable, String category1, String category2, String category3);
    //List<ItemsEntity> findByItem(String item);
    //Page<ItemsEntity> findByItem(String item, Pageable pageable);

    Page<ItemsEntity> findAllByCategoryEntity_Idx(Long idx, Pageable pageable);

    Page<ItemsEntity> findByItemContaining(String item, Pageable pageable);

    List<ItemsEntity> findAllByCategoryEntity_Idx(Long idx);

    List<ItemsEntity> findByItemContainingAndCategoryEntity_Idx(String item, Long idx);

}

