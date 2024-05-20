package portfolio1.Drink.Repository.Shopping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import portfolio1.Drink.Entity.Shopping.DeliveryEntity;
import portfolio1.Drink.Entity.Shopping.OrderEntity;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity,Long>
{
    List<DeliveryEntity> findAllByUserid(String userid);

    @Query(value="select * from drink_delivery where userid = :userid and to_date(substr(regdate,1,10),'yyyy-mm-dd') between to_date(:start,'yyyy-mm-dd') and to_date(:end,'yyyy-mm-dd')",nativeQuery = true)
    List<DeliveryEntity> findByToDate(@Param(value="userid") String userid, @Param(value = "start")String start, @Param(value = "end")String end);

    @Query(value="select * from drink_delivery where userid = :userid and substr(regdate,1,10) = :date",nativeQuery = true)
    List<DeliveryEntity> findByToStart(@Param(value = "userid")String userid, @Param(value="date")String date);
}
