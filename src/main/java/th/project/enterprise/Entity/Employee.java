package th.project.enterprise.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;



@Setter
@Getter
@MappedSuperclass // Define User as a mapped superclass
public abstract class Employee extends User {

    private String personnelNo;
    private float salary;
    private int vacationDays;
    private float workingHours;

    public Employee() {
        super();

    }

//    protected IService worksIn;


}

