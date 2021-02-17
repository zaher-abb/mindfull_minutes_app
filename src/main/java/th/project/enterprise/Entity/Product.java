package th.project.enterprise.Entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "eproduct")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private long price;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String pictureUrl;
    private String categorie;
    private String shortDescreption;
    private String longDescreption;


}
