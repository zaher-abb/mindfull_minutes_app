package th.project.enterprise.Service;

import th.project.enterprise.Entity.Product;
import th.project.enterprise.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;


    public List<Product> getAllProduct() {
        return (List<Product>) productRepository.findAll();
    }

    public Product getSingelProductById(long id) {
        Optional<Product> result = productRepository.findById(id);
        Product product;
        if (result.isPresent()) {
            product = result.get();
        } else {
            throw new RuntimeException("Did not find Product id");
        }
        return product;
    }

    public List<Product> getProductByName(String name) {

        if (name != null) {
            return productRepository.searchForProductWithNameOrDescreption(name);
        } else {
            return (List<Product>) productRepository.findAll();
        }
    }

    public List<Product> getAllProductWithCategorie(String categorie) {

        return productRepository.getAllProductWithCategorie(categorie);

    }


    public List<Product> sortProductByPriceAsc() {
        return productRepository.sortProductByPriceAsc();
    }

    public List<Product> sortProductByPriceDesc() {
        return productRepository.sortProductByPriceDesc();
    }


    public void addProduct(Product p1) {

        productRepository.save(p1);
    }


    public void deleteProductById(long pid) {
        productRepository.deleteById(pid);
    }


}
