package portfolio1.Drink.Repository.Shopping;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio1.Drink.Entity.Shopping.ItemLikeEntity;

public interface ItemLikesRepository extends JpaRepository<ItemLikeEntity, Long>
{
    ItemLikeEntity findByItemsEntity_idxAndUserid(Long idx, String userid);
}
