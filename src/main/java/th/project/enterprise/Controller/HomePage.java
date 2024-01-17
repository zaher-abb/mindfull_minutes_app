package th.project.enterprise.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.project.enterprise.Entity.Product;
import th.project.enterprise.Service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomePage {
    
    @GetMapping("start")
    public String showHomePage(Model model) {
        return "login";
    }
    @GetMapping("Home")
    public String startPage(Model model) {

        return "homePage";
    }
 

}

