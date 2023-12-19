package th.project.enterprise.Repository;

import th.project.enterprise.Entity.Adress;
import th.project.enterprise.Entity.Customer;
import th.project.enterprise.Entity.Product;
import th.project.enterprise.Entity.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepoistory extends CrudRepository<User, Long> {

    @Query("SELECT m from Customer m WHERE m.Email=:Email")
    Customer getUserByEmail(String Email);

    @Query("SELECT m FROM Customer m WHERE m.roles not LIKE %:role%")
    List<Customer> getAllCustomer(String role);
    @Modifying
    @Query("update Customer u set u.adress=:adress where u.id=:uid")
    void updateUserAdreesID(Adress adress, long uid);

//    @Query("SELECT m FROM Customer m WHERE m.roles not LIKE %:role%")
//    List<Customer> getAllCustomer(String role);
}
