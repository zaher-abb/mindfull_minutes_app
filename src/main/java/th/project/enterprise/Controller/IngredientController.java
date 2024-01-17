package th.project.enterprise.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.project.enterprise.Entity.Ingredient;
import th.project.enterprise.Entity.Product;
import th.project.enterprise.Service.IngredientService;
import th.project.enterprise.Service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/Ingredient")
public class IngredientController {

    
    @Autowired
    private IngredientService ingredientService;
    @GetMapping("/addIngredient")
    public String addIngredient(Model model) {
    
        model.addAttribute("ingredient", new Ingredient());
        
     
        return "addIngredient";
    }
    
    @PostMapping("/addIngredient")
    public String addIngredients(@ModelAttribute Ingredient ingredient) {
      
        List<String> ingredients = Arrays.asList(ingredient.getName().split(","));
        for (String name: ingredients
        ) {
            ingredientService.addIngredient(new Ingredient(name));
        }

        return "redirect:/Menu/all";
    }
}

