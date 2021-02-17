package th.project.enterprise.Repository;

import th.project.enterprise.Entity.Cart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

    @Modifying
    @Query("update Cart c set c.quantity=:number where c.product.id=:pid")
    void updateNumerOfIteams(long pid, int number);


    @Query("select c from Cart c where c.userID=:uid")
    List<Cart> findAllCartIteamForOneUser(long uid);


    @Query("select c from Cart c WHERE  c.product.id=:pid and c.userID=:uid")
    Cart getSingleCartIteam(long pid, long uid);


    @Modifying
    @Query("DELETE  from Cart c where c.userID=:uid")
    void deleteAllCartIteamByUserId(long uid);

    @Query("select count (c) from Cart c where  c.product.id=:pid and c.userID=:uid")
    int checkIfIteamAllreadyInCart(long pid, long uid);

    @Modifying
    @Query("DELETE  from Cart c where  c.product.id=:pid and c.userID=:uid")
    void deleteCartIteamByProductId(long pid, long uid);

    @Modifying
    @Query("update Cart c set c.quantity= c.quantity+1 where  c.product.id=:pid and c.userID=:uid")
    void updateQuantityifIteamAllreayInCart(long pid, long uid);

    @Query("select c.quantity from Cart c where  c.product.id=:pid and c.userID=:uid ")
    int getQuantityByProductId(long pid, long uid);


}
