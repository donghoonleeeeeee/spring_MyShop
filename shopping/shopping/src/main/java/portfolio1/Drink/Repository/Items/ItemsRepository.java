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
    Page<ItemsEntity> findAllByCategoryEntity_Idx(Long idx, Pageable pageable);

    Page<ItemsEntity> findByItemContaining(String item, Pageable pageable);

    List<ItemsEntity> findAllByCategoryEntity_Idx(Long idx);

    List<ItemsEntity> findByItemContainingAndCategoryEntity_Idx(String item, Long idx);


}

