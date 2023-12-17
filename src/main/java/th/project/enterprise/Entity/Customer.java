package th.project.enterprise.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "customer")
public class Customer extends User {


    public Customer() {
        super();
    }

}

