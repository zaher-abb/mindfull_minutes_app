
package th.project.enterprise.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.awt.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@Table(name = "DeliveryMan")

public class DeliveryMan extends Employee {

    private String driverLicence;

    public DeliveryMan(String personnelNo, String lastName, String firstName) {
        super(personnelNo, lastName,firstName);
        vacationDays = 25;
        salary = 2100;
        driverLicence = "XYZ123";
    }

    public DeliveryMan() {
        this(null, null, null);
    }
    
}

