package th.project.enterprise.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
    
    
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "product_ingreient_table",
      joinColumns = {
        @JoinColumn(name = "product_id", referencedColumnName = "id")
      },
      inverseJoinColumns = {
        @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
      }
    )
    @JsonManagedReference
     private Set<Ingredient> ingredients;



}
