package portfolio1.Drink.Repository.Shopping;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import portfolio1.Drink.Entity.Items.ItemsEntity;

import java.util.List;

@SpringBootTest
class ItemLikesRepositoryTest {

    @Autowired
    ItemLikesRepository itemLikesRepository;

    @Test
    void test() {

        List<ItemsEntity> byLikes = itemLikesRepository.findByLikes();
        System.out.println("================start================");
        for (ItemsEntity byLike : byLikes) {
            System.out.println("byLike = " + byLike.getItem());
        }
        System.out.println("================end================");


    }


}