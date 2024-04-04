package portfolio1.Drink.Repository.Community;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import portfolio1.Drink.Entity.Community.CommentEntity;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long>
{
    Page<CommentEntity> findAllByCommunityEntity_Idx(Long idx, Pageable pageable);
}
