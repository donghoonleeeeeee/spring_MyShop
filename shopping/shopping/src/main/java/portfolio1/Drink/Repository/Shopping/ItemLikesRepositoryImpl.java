/*
package portfolio1.Drink.Repository.Shopping;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import portfolio1.Drink.Entity.Items.ItemsEntity;
import portfolio1.Drink.Entity.Items.QItemsEntity;
import portfolio1.Drink.Entity.Shopping.QItemLikeEntity;

import java.util.List;

import static portfolio1.Drink.Entity.Items.QItemsEntity.*;
import static portfolio1.Drink.Entity.Shopping.QItemLikeEntity.*;

public class ItemLikesRepositoryImpl implements ItemLikesRepositoryCustom{

    private final JPAQueryFactory factory;
    public ItemLikesRepositoryImpl(EntityManager em) {
        this.factory = new JPAQueryFactory(em);
    }

    @Override
    public List<ItemsEntity> findByLikes() {

        return factory.selectFrom(itemsEntity)
                .join(itemsEntity.likeEntities, itemLikeEntity)
                .limit(10)
                .groupBy(itemsEntity)
                .orderBy(itemLikeEntity.count().desc())
                .fetch();
    }

}
*/
