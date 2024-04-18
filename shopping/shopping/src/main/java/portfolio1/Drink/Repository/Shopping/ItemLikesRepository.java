package portfolio1.Drink.Repository.Shopping;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import portfolio1.Drink.Entity.Shopping.ItemLikeEntity;

import java.util.List;

public interface ItemLikesRepository extends JpaRepository<ItemLikeEntity, Long>//, ItemLikesRepositoryCustom
{
    ItemLikeEntity findByItemsEntity_idxAndUserid(Long idx, String userid);
    List<ItemLikeEntity> findByItemsEntity_idx(Long idx);

    @Query(value = "select item_idx, count(item_idx) as count from item_likes group by item_idx order by count DESC fetch first 5 rows only", nativeQuery = true)
    List<Integer[]> BestItems();
}
