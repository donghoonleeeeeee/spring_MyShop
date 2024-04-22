package portfolio1.Drink.Repository.Shopping;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio1.Drink.Entity.Shopping.BasketEntity;

import java.util.List;

public interface BasketRepository extends JpaRepository<BasketEntity,Long>
{
    List<BasketEntity> findByUserid(String userid);
    List<BasketEntity> findByItemsEntity_Idx(Long idx);
}

