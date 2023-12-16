package th.project.enterprise.Entity;

import org.apache.catalina.LifecycleState;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {

    @OneToMany(fetch = FetchType.EAGER)

    private ArrayList<Order> order;


}
