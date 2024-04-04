package portfolio1.Drink.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import portfolio1.Drink.Entity.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity,Long>
{
    UserEntity findByUserid(String userid);

    List<UserEntity> findAllByUserid(String userid);

    boolean existsByUserid(String userid);
}
