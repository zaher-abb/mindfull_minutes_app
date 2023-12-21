package th.project.enterprise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import th.project.enterprise.Entity.Ingredient;
import th.project.enterprise.Entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT m FROM Product m WHERE m.name LIKE %:name% or m.shortDescreption LIKE %:name%")
    List<Product> searchForProductWithNameOrDescreption(String name);

    @Query("SELECT m FROM Product m WHERE m.categorie LIKE %:categorie%")
    List<Product> getAllProductWithCategorie(String categorie);


    @Query("SELECT m FROM Product m  order by m.price asc")
    List<Product> sortProductByPriceAsc();

    @Query("SELECT m FROM Product m order by m.price desc ")
    List<Product> sortProductByPriceDesc();
    
    
    
    
}
