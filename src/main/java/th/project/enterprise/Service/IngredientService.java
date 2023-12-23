package th.project.enterprise.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.project.enterprise.Entity.Ingredient;
import th.project.enterprise.Entity.Product;
import th.project.enterprise.Repository.IngredientRepo;
import th.project.enterprise.Repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {


    @Autowired
    private IngredientRepo ingredientRepo;


    public List<Ingredient> getAllIngredient() {
        return (List<Ingredient>) ingredientRepo.findAll();
    }


    
    
    
    
}
