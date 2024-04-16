package portfolio1.Drink.Repository.Shopping;


import org.springframework.data.jpa.repository.JpaRepository;
import portfolio1.Drink.Entity.Shopping.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>
{

}
