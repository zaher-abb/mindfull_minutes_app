package th.project.enterprise.Repository;

import th.project.enterprise.Entity.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {


    @Modifying
    @Query("delete from Order o where o.status = 'in Bearbeitung' and o.user.id=:uid ")
    void deleteOrderTransaktionByUserId(long uid);

    @Query("select o.orderId from Order o  WHERE o.user.id=:uid and o.status='in Bearbeitung' ")
    long getOrderIdInBearbeitung(long uid);

    @Query("select o from Order o  WHERE o.user.id=:uid and o.status = 'opend'")
    Order getOrderOpend(long uid);

    @Query("select count (o) from  Order o where o.status = 'in Bearbeitung' and o.user.id=:uid")
    int checkIfUserhasOrderInBearbeitung(long uid);

    @Query("select o from Order o  WHERE o.user.id=:uid and o.status='in Bearbeitung' ")
    List<Order> getAllOrderInBearbeitung(long uid);

    @Modifying
    @Query("update Order o set o.totalAmount=:total  WHERE o.orderId=:oid")
    void updateTotalAmounte(long total, long oid);


}
