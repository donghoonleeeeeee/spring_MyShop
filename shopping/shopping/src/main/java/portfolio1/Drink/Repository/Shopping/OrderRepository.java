package portfolio1.Drink.Repository.Shopping;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import portfolio1.Drink.Entity.Shopping.OrderEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>
{
    List<OrderEntity> findAllByUserid(String userid);

    @Query(value="select * from drink_order where userid = :userid and to_date(substr(regdate,1,10),'yyyy-mm-dd') between to_date(:start,'yyyy-mm-dd') and to_date(:end,'yyyy-mm-dd')",nativeQuery = true)
    List<OrderEntity> findByToDate(@Param(value="userid") String userid, @Param(value = "start")String start, @Param(value = "end")String end);

    @Query(value="select * from drink_order where userid = :userid and substr(regdate,1,10) = :date",nativeQuery = true)
    List<OrderEntity> findByToStart(@Param(value = "userid")String userid, @Param(value="date")String date);
}
