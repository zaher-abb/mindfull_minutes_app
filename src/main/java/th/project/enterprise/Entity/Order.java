package th.project.enterprise.Entity;


import lombok.Getter;

import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Setter
@Getter

@Table(name = "eorder")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    private LocalDateTime creatDate;
    private LocalDateTime deliveryDate;

    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private long totalAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adress_id")
    private Adress adress;

    public Order(LocalDateTime creatDate, User user, long totalAmount) {
        this.creatDate = creatDate;
        this.user = user;
        deliveryDate = this.creatDate.plusDays(7);
        this.setStatus("opend");
        this.totalAmount = totalAmount;

    }

    public Order(LocalDateTime creatDate, User user, Adress adress) {
        this.creatDate = creatDate;
        this.user = user;
        this.adress = adress;
    }

    public Order(LocalDateTime creatDate) {
        this.creatDate = creatDate;

    }

    public Order() {

    }

    public void setTotalAmount(long totalAmount) {

        this.totalAmount = Math.max(totalAmount, 0);
    }

}
