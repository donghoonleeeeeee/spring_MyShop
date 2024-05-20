package portfolio1.Drink;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import portfolio1.Drink.Repository.Community.CommentRepository;
import portfolio1.Drink.Repository.Community.CommunityRepository;
import portfolio1.Drink.Repository.Items.ItemCategoryRepository;
import portfolio1.Drink.Repository.Items.ItemsRepository;
import portfolio1.Drink.Repository.Shopping.BasketRepository;
import portfolio1.Drink.Repository.Shopping.ItemLikesRepository;
import portfolio1.Drink.Repository.Shopping.OrderRepository;
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

	@Autowired
	BasketRepository basketRepository;

	@Autowired
	ItemLikesRepository itemLikesRepository;

	@Autowired
	OrderRepository orderRepository;

	@Test
	void 카테고리()
	{
		String start = "2024-04-24";
		String end = "2024-04-25";
		System.out.println(orderRepository.findByToStart("new1",start).size());
	}


}
