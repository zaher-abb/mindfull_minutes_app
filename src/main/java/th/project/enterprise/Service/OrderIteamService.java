package th.project.enterprise.Service;

import th.project.enterprise.Entity.Cart;
import th.project.enterprise.Entity.Order;
import th.project.enterprise.Entity.OrderIteam;
import th.project.enterprise.Repository.OrderIteamRepository;
import th.project.enterprise.Repository.OrderRepository;
import th.project.enterprise.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderIteamService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderIteamRepository orderIteamRepository;
    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepository;


    public void creatOrderIteams(long uid) {

        Order order1 = orderRepository.getOrder(uid);
        List<Cart> allCartIteams = cartService.getAllCartIteams(uid);
        for (Cart c : allCartIteams) {
            OrderIteam order_itemas1 = new OrderIteam(order1, c.getProduct(), c.getUserID(), c.getQuantity(), c.getTotalAmountSingleCartIteam());

            orderIteamRepository.save(order_itemas1);
        }
    }


    public void deleteAllOrderIteamsTransaktion(long oid) {
        orderIteamRepository.deleteOrderIteamTransaktion(oid);
    }

    public void deleteOrderIteamAfterConfrmation(long uid) {
        orderIteamRepository.deleteOrderIteamAfterConfrmation(uid);
    }


}




