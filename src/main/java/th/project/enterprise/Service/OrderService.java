package th.project.enterprise.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.project.enterprise.Entity.*;
import th.project.enterprise.Repository.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    OrderIteamRepository orderIteamRepository;

    @Autowired
    AdressRepository adressRepository;

    @Autowired
    VoucherRepository voucherRepository;

    public void creatOrder(Customer user, long totalAmount) {

        LocalDateTime Date = LocalDateTime.now();
        Order order1 = new Order(Date, user, totalAmount);
        orderRepository.save(order1);
    }

    public Order getOrder(long uid) {
        return orderRepository.getOrder(uid);
    }

    public long getOrderIdInBearbeitung(long uid) {
        return orderRepository.getOrderIdInBearbeitung(uid);
    }

    public void deleteOrderByUserId(long uid) {
        orderRepository.deleteOrderTransaktionByUserId(uid);
    }

    public List<OrderIteam> getAllOrderIteamsProduct(long uid, long oid) {
        return orderIteamRepository.getAllOrderIteamByUserId(uid, oid);
    }

    public int getSumTotalPrice(long uid) {
        return orderIteamRepository.getSumOrderTotalPrice(uid);
    }

    public Order getOrderByOrderId(long oid) {
        Optional<Order> result = orderRepository.findById(oid);
        Order order1;
        if (result.isPresent()) {
            order1 = result.get();
        } else {
            throw new RuntimeException("Did not find THE order id");
        }
        return order1;
    }

    public Voucher checkIfCodeIsAvailable(String couponCode) {

        if (voucherRepository.checkIfCodeIsAvailable(couponCode) == 1) {

            return voucherRepository.getVoucherByCouponCode(couponCode);
        } else {
            return null;
        }
    }

    /*
        public List<Voucher> getVoucherByOrderId(long oid) {
            return voucherRepository.getVoucherByOrderId(oid);
        }
    */
    public boolean checkIfUserhasOrderInBearbeitung(long uid) {

        if (orderRepository.checkIfUserhasOrderInBearbeitung(uid) >= 1) {
            return true;
        }
        return false;
    }


    public void cancelledOrderStatus(long uid) {
        List<Order> orderList = orderRepository.getAllOrderInBearbeitung(uid);
        for (Order o : orderList) {
            /*o.setStatus("cancelled");*/
            orderIteamRepository.deleteAllOrderIteamWithCancelledStatus(o.getOrderId());
            orderRepository.delete(o);

        }

    }

    public void updateOrderAdress(Adress adr, long oid) {
        adressRepository.updateOrderAdress(adr, oid);
    }

    public void updateVorcherStatus(long vid) {
        voucherRepository.updateVorcherStatus(vid);
    }

    public void updateTotalAmounte(long total, long oid) {
        orderRepository.updateTotalAmounte(total, oid);
    }

}



