package th.project.enterprise.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "ecart")
public class Cart {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartID;
    private long userID;
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productid")
    private Product product;

    private long totalAmountSingleCartIteam;

    public Cart() {
    }

    public Cart(Product product, long userID) {
        this.userID = userID;
        this.product = product;
        this.setQuantity(1);
    }


}


