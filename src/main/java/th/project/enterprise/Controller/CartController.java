package th.project.enterprise.Controller;

import th.project.enterprise.Entity.Cart;
import th.project.enterprise.Entity.User;
import th.project.enterprise.Service.CartService;
import th.project.enterprise.Service.OrderIteamService;
import th.project.enterprise.Service.ProductService;
import th.project.enterprise.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/Cart")
public class CartController {


    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;


    @Autowired
    OrderIteamService orderIteamService;

    @Autowired
    ProductService productService;

    @GetMapping("/addToCart")
    public String addToCart(@Param("id") long id, Principal principal, Model model) {

        User user1 = userService.findByEmail(principal.getName());
        if (user1 == null) {
            return "redirect:/User/logout";
        } else {
            long userid = user1.getId();
            cartService.addToCart(id, userid);

            int quantity = cartService.getQuantityByProductId(id, userid);

            model.addAttribute("q", quantity);

            return "redirect:/Cart/viewCart";
        }
    }


    @GetMapping("/viewCart")
    public String viewCart(Model model, Principal principal) {
        User user1 = userService.findByEmail(principal.getName());
        if (user1 == null) {
            return "redirect:/User/logout";
        } else {
            long userid = user1.getId();
            List<Cart> carts = cartService.getAllCartIteams(userid);

            if (carts.size() == 0) {
                return "redirect:/Product/Home";
            } else {
                model.addAttribute("carts", carts);
            }
            return "cart";

        }
    }

    @GetMapping("/DeleteSingleIteam")
    public String deleteSingleiteamFromCart(@Param("Pid") long Pid, Principal principal) {
        User user1 = userService.findByEmail(principal.getName());

        if (user1 == null) {
            return "redirect:/User/logout";
        } else {
            long uid = user1.getId();

            cartService.deleteSingleCartIteam(Pid, uid);

            return "redirect:/Cart/viewCart";
        }
    }

    @GetMapping("/updateNumerOfItems")
    public String updateNumerOfItems(@Param("Pid") long Pid, @Param("quantity") int quantity) {

        cartService.updateNumerOfIteams(Pid, quantity);

        return "redirect:/Cart/viewCart";
    }


}
