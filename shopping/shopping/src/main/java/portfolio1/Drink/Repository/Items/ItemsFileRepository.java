package portfolio1.Drink.Repository.Items;


import org.springframework.data.jpa.repository.JpaRepository;
import portfolio1.Drink.Entity.Items.ItemsFileEntity;

import java.util.List;

public interface ItemsFileRepository extends JpaRepository<ItemsFileEntity,Long>
{
    List<ItemsFileEntity> findByItemsEntity_Idx(Long tidx);
}
