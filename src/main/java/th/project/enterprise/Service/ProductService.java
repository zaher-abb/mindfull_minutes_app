package th.project.enterprise.Service;

import th.project.enterprise.Entity.Ingredient;
import th.project.enterprise.Entity.Product;
import th.project.enterprise.Repository.IngredientRepo;
import th.project.enterprise.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private IngredientRepo ingredientRepo;
    
    
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


    public void addProduct(Product p1, List<Long> ingredientIds) {
    
        Set<Ingredient> objectSet = new HashSet<>();
        for (Long value : ingredientIds) {
            Optional<Ingredient> ing = ingredientRepo.findById(value);
            objectSet.add(ing.get());
        }
        
        p1.setIngredients(objectSet);
    
        productRepository.save(p1);
    }
    
   
    
    
    
    public void deleteProductById(long pid) {
        productRepository.deleteById(pid);
    }
    
    @Transactional
    public void updateProduct(Product p) {
    
        productRepository.save(p);
        
    }
    
    public void addIngredientToProduct(Long productId, List<Long> ingredientIds) {
        Optional<Product> product = productRepository.findById(productId);
        Set<Ingredient> ingredients = ingredientRepo.findAllById(ingredientIds).stream().collect(Collectors.toSet());;
    
        
            product.get().getIngredients().addAll(ingredients);;
            productRepository.save(product.get());
        
    }
    public void removeIngredientFromProduct(Long productId, List<Long> ingredientIds) {
        Optional<Product> product = productRepository.findById(productId);
        Set<Ingredient> ingredients = ingredientRepo.findAllById(ingredientIds).stream().collect(Collectors.toSet());
        
        product.get().getIngredients().removeAll(ingredients);
        productRepository.save(product.get());
    }
}
