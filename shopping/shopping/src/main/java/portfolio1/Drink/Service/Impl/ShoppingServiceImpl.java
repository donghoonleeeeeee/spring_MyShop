package portfolio1.Drink.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import portfolio1.Drink.DTO.Items.ItemsDTO;
import portfolio1.Drink.DTO.Shopping.*;
import portfolio1.Drink.DTO.Users.UserDTO;
import portfolio1.Drink.Entity.Shopping.DeliveryEntity;
import portfolio1.Drink.Entity.Shopping.ItemLikeEntity;
import portfolio1.Drink.Entity.Items.ItemsEntity;
import portfolio1.Drink.Entity.Shopping.OrderEntity;
import portfolio1.Drink.Entity.Shopping.BasketEntity;
import portfolio1.Drink.Entity.UserEntity;
import portfolio1.Drink.Repository.Items.ItemCategoryRepository;
import portfolio1.Drink.Repository.Items.ItemsRepository;
import portfolio1.Drink.Repository.Shopping.BasketRepository;
import portfolio1.Drink.Repository.Shopping.DeliveryRepository;
import portfolio1.Drink.Repository.Shopping.ItemLikesRepository;
import portfolio1.Drink.Repository.Shopping.OrderRepository;
import portfolio1.Drink.Repository.UserRepository;
import portfolio1.Drink.Service.ItemsService;
import portfolio1.Drink.Service.ShoppingService;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingServiceImpl implements ShoppingService
{
    private final ItemsRepository itemsRepository;
    private final ItemLikesRepository itemLikesRepository;

    private final UserRepository userRepository;
    private final BasketRepository basketRepository;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(ItemsService.class);
    @Override
    public void InputBasket(BasketDTO basketDTO, Principal principal)
    {
        ItemsEntity item = itemsRepository.findById(basketDTO.getItem_idx()).orElse(null);
        LOGGER.info("[장바구니] 새로운 물품을 등록합니다. / 제품명: "+item.getItem()+", 수량: "+basketDTO.getQuantity());
        basketDTO.setUserid(principal.getName());
        BasketEntity basket = basketDTO.toEntity();
        basket.setItemsEntity(item);
        basketRepository.save(basket);
    }

    @Override
    public List<BasketListDTO> UserBasket(Principal principal)
    {
        List<BasketEntity> UserBasket = basketRepository.findByUserid(principal.getName());
        List<BasketListDTO> BasketList = new ArrayList<>();

        for(int a=0; a<UserBasket.size(); a++)
        {
            BasketListDTO list = new BasketListDTO();
            list.setBasket_idx(UserBasket.get(a).getIdx());
            list.setItem_idx(UserBasket.get(a).getItemsEntity().getIdx());
            list.setQuantity(UserBasket.get(a).getQuantity());
            list.setItemName(UserBasket.get(a).getItemsEntity().getItem());
            list.setThumbnail(UserBasket.get(a).getItemsEntity().getItemsFileEntities().get(0).getPath());
            list.setPrice(UserBasket.get(a).getItemsEntity().getPrice());
            BasketList.add(list);
        }
        return BasketList;
    }

    @Override
    public List<BasketListDTO> Payment(PaymentDTO paymentDTO)
    {
        BasketEntity basket = null;
        List<BasketListDTO> BasketList = new ArrayList<>();
        for(int a=0; a<paymentDTO.getBasket_idx().length; a++)
        {
            if(!paymentDTO.getBasket_idx()[a].equals("none"))
            {
                BasketListDTO list = new BasketListDTO();
                basket = basketRepository.findById(Long.valueOf(paymentDTO.getBasket_idx()[a])).orElse(null);
                basket.setQuantity(Integer.parseInt(paymentDTO.getItem_quantity()[a]));

                list.setBasket_idx(basket.getIdx());
                list.setItem_idx(basket.getItemsEntity().getIdx());
                list.setQuantity(basket.getQuantity());
                list.setItemName(basket.getItemsEntity().getItem());
                list.setThumbnail(basket.getItemsEntity().getItemsFileEntities().get(0).getPath());
                list.setPrice(basket.getItemsEntity().getPrice());
                BasketList.add(list);
                basketRepository.save(basket);
            }
        }
        return BasketList;
    }

    @Override
    public UserDTO Users(Principal principal)
    {
        UserEntity user = userRepository.findByUserid(principal.getName());

        return user.toDTO();
    }

    @Override
    public void PayResult(OrderDTO orderDTO, DeliveryDTO deliveryDTO, Principal principal)
    {
        ItemsEntity items = null;
        BasketEntity basket = null;
        Date now = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int cash = 0;
        LOGGER.info("[결제] 결제를 진행합니다.");
        for(int a=0; a<orderDTO.getItem_idx().size(); a++)
        {
            cash = cash + orderDTO.getTotal_pay().get(a);
            basket = basketRepository.findById(Long.valueOf(orderDTO.getBasket_idx().get(a))).orElse(null);
            if(basket != null)
            {
                basketRepository.delete(basket);
            }
            LOGGER.info("[결제] "+(a+1)+"번째 장바구니를 제거합니다.");
            items = itemsRepository.findById(Long.valueOf(orderDTO.getItem_idx().get(a))).orElse(null);
            OrderEntity order = new OrderEntity();
            order.setItemsEntity(items);
            order.setQuantity(orderDTO.getItem_quantity().get(a));
            order.setPay_type(orderDTO.getPay());
            order.setTotal_pay(orderDTO.getTotal_pay().get(a));
            order.setUserid(principal.getName());
            order.setRegdate(sd.format(now));
            LOGGER.info("[결제] "+(a+1)+"번째 아이템을 주문 목록에 등록합니다. / 물품명: "+items.getItem());
            orderRepository.save(order);
        }

        UserEntity user = userRepository.findByUserid(principal.getName());
        user.setCash(user.getCash()-cash);
        userRepository.save(user);

        DeliveryEntity delivery = deliveryDTO.toEntity();
        delivery.setUserid(principal.getName());
        LOGGER.info("[결제] 배송정보를 등록합니다.");
        deliveryRepository.save(delivery);

    }

    @Override
    public boolean ItemLikes(Long idx, Principal principal)
    {
        Date now = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(principal == null)
        {
            return false;
        }
        else
        {
            List<ItemLikeEntity> list = itemLikesRepository.findAll();
            List<Long> item_idx = new ArrayList<>();
            List<String> userid = new ArrayList<>();

            for(int a=0; a<list.size(); a++)
            {
                userid.add(list.get(a).getUserid());
                item_idx.add(list.get(a).getItemsEntity().getIdx());
            }

            if(item_idx.contains(idx) && userid.contains(principal.getName())) // 추천 목록에 userid가 있으면
            {
                ItemLikeEntity like = itemLikesRepository.findByItemsEntity_idxAndUserid(idx,principal.getName());
                itemLikesRepository.delete(like);
            }
            else
            {
                ItemLikeEntity likes = new ItemLikeEntity();
                likes.setUserid(principal.getName());
                likes.setItemsEntity(itemsRepository.findById(idx).orElse(null));
                likes.setRegdate(sd.format(now));

                itemLikesRepository.save(likes);
            }

            return true;
        }
    }
    @Override
    public Integer LikeCount(Long idx)
    {
        return itemLikesRepository.findByItemsEntity_idx(idx).size();
    }

    @Override
    public List<ItemsDTO> NewAddItems()
    {
        List<ItemsEntity> entities = itemsRepository.findAll(Sort.by(Sort.Direction.DESC,"idx"));
        List<ItemsDTO> dtos = new ArrayList<>();
        for(int a=0; a<10; a++)
        {
            ItemsDTO dto = entities.get(a).toDTO();
            dtos.add(dto);
        }
        return dtos;
    }

}
