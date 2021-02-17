package th.project.enterprise.Entity;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "evoucher")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long voucherID;

    @Min(6)
    @UniqueElements
    private long SerialNumber;
    private String pin;
    private int amount;
    private int available;

}
