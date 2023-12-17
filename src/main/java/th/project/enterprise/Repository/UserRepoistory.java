package th.project.enterprise.Repository;

import th.project.enterprise.Entity.Adress;
import th.project.enterprise.Entity.Customer;
import th.project.enterprise.Entity.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepoistory extends CrudRepository<User, Long> {

    @Query("SELECT m from Customer m WHERE m.Email=:Email")
    Customer getUserByEmail(String Email);

    @Modifying
    @Query("update Customer u set u.adress=:adress where u.id=:uid")
    void updateUserAdreesID(Adress adress, long uid);
}
