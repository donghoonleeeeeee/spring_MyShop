package portfolio1.Drink;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import portfolio1.Drink.Repository.Community.CommentRepository;
import portfolio1.Drink.Repository.Community.CommunityRepository;
import portfolio1.Drink.Repository.Items.ItemCategoryRepository;
import portfolio1.Drink.Repository.Items.ItemsRepository;
import portfolio1.Drink.Service.ItemsService;

@SpringBootTest
class DrinkApplicationTests {

	@Autowired
	ItemsRepository itemsRepository;
	
	@Autowired
	CommunityRepository communityRepository;

	@Autowired
	ItemCategoryRepository itemCategoryRepository;

	@Autowired
	ItemsService itemsService;

	@Autowired
	CommentRepository commentRepository;

	@Test
	void 카테고리()
	{
		System.out.println(itemsRepository.findAllByCategoryEntity_Idx(28L).size());
	}


}
