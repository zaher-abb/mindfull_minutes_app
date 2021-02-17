package th.project.enterprise.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "e_order_iteams")
public class OrderIteam {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long order_iteams_ID;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pid")
    private Product product;

    private long uid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderid")
    private Order order;
    private double totalamountSingelIteam;

    private int quantity;

    public OrderIteam(Order order, Product product, long uid, int quantity, double totalamountSingelIteam) {
        this.product = product;
        this.uid = uid;
        this.order = order;
        this.quantity = quantity;
        this.totalamountSingelIteam = totalamountSingelIteam;

    }
}
