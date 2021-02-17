package th.project.enterprise.Repository;

import th.project.enterprise.Entity.OrderIteam;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderIteamRepository extends CrudRepository<OrderIteam, Long> {


    @Query("SELECT  oi  FROM OrderIteam oi ,Order o WHERE o.orderId=:oid and  oi.uid=:uid")
    List<OrderIteam> getAllOrderIteamByUserId(long uid, long oid);

    @Query("SELECT sum(c.totalAmountSingleCartIteam)FROM Cart c  WHERE c.userID=:uid  ")
    int getSumOrderTotalPrice(long uid);

    @Modifying
    @Query("DELETE  from OrderIteam oi where oi.order.orderId=:oid ")
    void deleteAllOrderIteamWithCancelledStatus(long oid);


    @Modifying
    @Query("delete from OrderIteam oi where oi.order.orderId=:oid")
    void deleteOrderIteamTransaktion(long oid);


    @Modifying
    @Query("delete from OrderIteam oi where oi.uid=:uid")
    void deleteOrderIteamAfterConfrmation(long uid);


}
