package portfolio1.Drink.Repository.Community;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import portfolio1.Drink.Entity.Community.CommunityEntity;

import java.util.List;

public interface CommunityRepository extends JpaRepository<CommunityEntity,Long>
{
    Page<CommunityEntity> findAllByType(String type, Pageable pageable);


}

