package th.project.enterprise.Controller;

import th.project.enterprise.Entity.Product;
import th.project.enterprise.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/Product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/Home")
    public String showHomePage(Model model) {
        List<Product> products = productService.getAllProduct();
        model.addAttribute("p1", products);
        return "main";
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
    public String pizza(Model model) {
        List<Product> products = productService.getAllProductWithCategorie("pizza");
        model.addAttribute("p1", products);
        return "main";
    }

    @GetMapping("/pasta")
    public String pasta(Model model) {
        List<Product> products = productService.getAllProductWithCategorie("pasta");
        model.addAttribute("p1", products);
        return "main";
    }
    @GetMapping("/dessert")
    public String Dessert(Model model) {
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


}

