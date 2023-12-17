package th.project.enterprise.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.awt.*;

@Setter
@Getter
@Entity
@Table(name = "Chef")

public class Chef extends Employee {

    private Color colorApron;
    public Chef() {
        super();
    }

}

