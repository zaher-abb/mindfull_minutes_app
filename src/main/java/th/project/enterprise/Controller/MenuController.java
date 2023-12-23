package th.project.enterprise.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import th.project.enterprise.Entity.FileUploader;
import th.project.enterprise.Entity.Ingredient;
import th.project.enterprise.Entity.Product;
import th.project.enterprise.Repository.IngredientRepo;
import th.project.enterprise.Service.EmailService;
import th.project.enterprise.Service.IngredientService;
import th.project.enterprise.Service.ProductService;
import th.project.enterprise.Service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping("/Menu")
public class MenuController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private IngredientService ingredientService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    EmailService emailService;

   @GetMapping("/all")
    public String showHomePage(Model model) {

        List<Product> products = productService.getAllProduct();

        model.addAttribute("p1", products);
        return "Menu";
    }

    @GetMapping("/search")
    public String search(Model model, @Param("search") String search, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            List<Product> products = productService.getProductByName(search);
            model.addAttribute("p1", products);
            return "AdminSeite";
        } else {
            List<Product> products = productService.getProductByName(search);
            model.addAttribute("p1", products);
            return "main";
        }
    }

    @GetMapping("/pizza")
    public String Laptops(Model model) {
        List<Product> products = productService.getAllProductWithCategorie("pizza");
        model.addAttribute("p1", products);
        return "main";
    }

    @GetMapping("/pasta")
    public String sport(Model model) {
        List<Product> products = productService.getAllProductWithCategorie("pasta");
        model.addAttribute("p1", products);
        return "main";
    }


    @GetMapping("view")
    public String viewSingelProduct(@Param("Pid") long Pid, Model model) {
        Product products = productService.getSingelProductById(Pid);
        model.addAttribute("p1", products);

        return "singelView";
    }


    @GetMapping("/orderByPriceAsc")
    public String OrderByPriceAsc(Model model) {
        List<Product> products = productService.sortProductByPriceAsc();
        model.addAttribute("p1", products);
        return "main";
    }

    @GetMapping("/orderByPriceDesc")
    public String OrderByPriceDesc(Model model) {
        List<Product> products = productService.sortProductByPriceDesc();
        model.addAttribute("p1", products);
        return "main";
    }
    @GetMapping("/delete")
    public String deleteProudct(@Param("id") long id) {
        productService.deleteProductById(id);
        
        return "redirect:/Menu/all";
    }
    @GetMapping("/viewAddProductPage")
    public String viewPageaddProductFromAdmin(Model model) {
        
        model.addAttribute("p1", new Product());
        model.addAttribute("allIngredients", ingredientService.getAllIngredient());
        return "addProduct";
    }
    
    @GetMapping("/update")
    public String viewUpdateForm(@Param("id") long id, Model model) {
        Product product = productService.getSingelProductById(id);
      List<Ingredient> ingredients = ingredientService.getAllIngredient();
        
        model.addAttribute("p1", product);
        model.addAttribute("allIngredients",ingredients);
  
      return "updateProduct";
    }
    
    @PostMapping("/AddProduct")
    public String uploadFile(@Param("image") MultipartFile image,@RequestParam List<Long> ingredientIds, Product p) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        p.setPictureUrl("/images/" + fileName);
        String uploadDir = "C:\\Users\\zzermani\\OneDrive - Technische Hochschule Brandenburg\\Desktop\\WinterSemester 2023-24\\SAQS\\Projekt\\Projekt-final-19.12.2023\\Online-Shop-Spring-Boot\\src\\main\\resources\\static\\images";
        
        
        
        FileUploader.saveFile(uploadDir, fileName, image);
        productService.addProduct(p,ingredientIds);
//        productService.addIngredientToProduct(p.getId(),ingredientIds);
  
      return "redirect:/Menu/all";
    }
  
  @PostMapping("/updateProduct")
  public String updateProduct(@Param("image") MultipartFile image,@Param("id") long id,@Param("price") long price, Product p) throws IOException {
     Product pr = productService.getSingelProductById(id);
    p.setPictureUrl(pr.getPictureUrl());
    p.setIngredients(pr.getIngredients());
    
    if( !image.isEmpty() ){
       String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
       p.setPictureUrl("/images/" + fileName);
       String uploadDir = "C:\\Users\\zzermani\\OneDrive - Technische Hochschule Brandenburg\\Desktop\\WinterSemester 2023-24\\SAQS\\Projekt\\Projekt-final-19.12.2023\\Online-Shop-Spring-Boot\\src\\main\\resources\\static\\images";
       FileUploader.saveFile(uploadDir, fileName, image);
  
     }
    p.setId(id);
    p.setPrice(price);
    productService.updateProduct(p);
    return "redirect:/Menu/all";
  }
  
  
  @PostMapping("/{productId}/addIngredient")
  public String addIngredientToProduct(@PathVariable Long productId, @RequestParam List<Long> ingredientIds, Model model) {
  
    Product product = productService.getSingelProductById(productId);
    List<Ingredient> ingredients = ingredientService.getAllIngredient();
  
    model.addAttribute("p1", product);
    model.addAttribute("allIngredients",ingredients);
    
    productService.addIngredientToProduct(productId, ingredientIds);
    model.addAttribute("id",productId);
    
//    return "redirect:/Menu/update?id="+productId;
    return "redirect:/Menu/all";
  }
  @PostMapping("/{productId}/removeIngredient")
  public String removeIngredientFromProduct(@PathVariable Long productId, @RequestParam List<Long> ingredientIds, Model model) {
  
    Product product = productService.getSingelProductById(productId);
    List<Ingredient> ingredients = ingredientService.getAllIngredient();
  
    model.addAttribute("p1", product);
    model.addAttribute("allIngredients",ingredients);
   
    productService.removeIngredientFromProduct(productId, ingredientIds);
    model.addAttribute("id",productId);
    return "redirect:/Menu/all";
  }
    
}

