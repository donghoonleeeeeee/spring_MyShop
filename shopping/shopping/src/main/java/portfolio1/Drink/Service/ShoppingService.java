package portfolio1.Drink.Service;

import portfolio1.Drink.DTO.Items.ItemsDTO;
import portfolio1.Drink.DTO.Shopping.*;
import portfolio1.Drink.DTO.Users.UserDTO;
import portfolio1.Drink.Entity.Shopping.BasketEntity;

import java.security.Principal;
import java.util.List;

public interface ShoppingService
{
    void InputBasket(BasketDTO basketDTO, Principal principal);
    List<BasketListDTO> UserBasket(Principal principal);
    List<BasketListDTO> Payment(PaymentDTO paymentDTO);
    UserDTO Users(Principal principal);
    void PayResult(OrderDTO orderDTO, DeliveryDTO deliveryDTO, Principal principal);
    boolean ItemLikes(Long idx, Principal principal);
    Integer LikeCount(Long idx);
    List<ItemsDTO> NewAddItems();
}
