package th.project.enterprise.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import th.project.enterprise.Entity.*;
import th.project.enterprise.Service.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/Order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderIteamService orderIteamService;
    @Autowired
    UserService userService;
    @Autowired
    CartService cartService;
    @Autowired
    AdressService adressService;
    @Autowired
    EmailService emailService;


    @GetMapping("/CreatOrder")
    public String creatOrder(Principal principal, Model model) {
        Customer user1 = userService.findByEmail(principal.getName());
        if (user1 == null) {
            return "redirect:/User/logout";
        } else {

            if (orderService.checkIfUserhasOrderStarted(user1.getId())) {

                System.out.println("USER hast noch order Started ");
                orderService.cancelledOrderStatus(user1.getId());
            }

            orderService.creatOrder(user1, orderService.getSumTotalPrice(user1.getId()));
            Order order1 = orderService.getOrder(user1.getId());
            orderIteamService.creatOrderIteams(user1.getId());
            List<OrderIteam> orderIteams = orderService.getAllOrderIteamsProduct(user1.getId(), order1.getOrderId());
            model.addAttribute("orderIteams", orderIteams);
            model.addAttribute("totalAmount", order1.getTotalAmount());
            model.addAttribute("adress", new Adress());
            return "checkout";
        }
    }


    @GetMapping("/BackToCart")
    public String deleteOrderInBearbeitung(Principal principal) {

        Customer user1 = userService.findByEmail(principal.getName());
        if (user1 == null) {
            return "redirect:/User/logout";
        } else {
            long orderId = orderService.getOrderIdInBearbeitung(user1.getId());
            orderIteamService.deleteAllOrderIteamsTransaktion(orderId);
            orderService.deleteOrderByUserId(user1.getId());
            return "redirect:/Cart/viewCart";
        }
    }

    @GetMapping("/userSameAddress")
    public String userSameAddress(Principal principal, Model model) {
        Customer user1 = userService.findByEmail(principal.getName());
        long orderid = orderService.getOrderIdInBearbeitung(user1.getId());
        Order order1 = orderService.getOrderByOrderId(orderid);
        if (user1.getAdress() == null) {
            orderIteamService.deleteAllOrderIteamsTransaktion(orderid);
            orderService.deleteOrderByUserId(user1.getId());
            return "redirect:/User/showUpdateAdressForm";
        } else {
            orderService.updateOrderAdress(user1.getAdress(), orderid);
            order1.setAdress(user1.getAdress());
            order1.setTotalAmount(orderService.getSumTotalPrice(user1.getId()));
            model.addAttribute("totalprice", order1.getTotalAmount());

            model.addAttribute("adr", order1.getAdress());
            LocalDateTime date = order1.getDeliveryDate();
            model.addAttribute("date", date);
            return "confirmation";
        }
    }


    @GetMapping("/viewConfirmedPage")
    public String viewConfirmedPage(Principal principal, Model model) {
        Customer user1 = userService.findByEmail(principal.getName());
        if (user1 == null) {
            return "redirect:/User/logout";
        } else {
            long orderid = orderService.getOrderIdInBearbeitung(user1.getId());
            Order order1 = orderService.getOrderByOrderId(orderid);
            if (order1.getAdress() == null) {
                orderService.deleteOrderByUserId(user1.getId());
                return "redirect:/User/showUpdateAdressForm";
            } else {
                model.addAttribute("totalprice", order1.getTotalAmount());

                model.addAttribute("adr", order1.getAdress());
                LocalDateTime date = order1.getDeliveryDate();
                model.addAttribute("date", date);
                return "confirmation";
            }
        }
    }

    @PostMapping("/addAdresstoOrder")
    public String addAdressToOrder(@Valid Adress adr, BindingResult result, Principal principal, Model model) {

        if (result.hasErrors()) {
            return "checkout";
        }
        Customer user1 = userService.findByEmail(principal.getName());
        if (user1 == null) {
            return "redirect:/User/logout";
        } else {
            long orderid = orderService.getOrderIdInBearbeitung(user1.getId());
            Order order1 = orderService.getOrderByOrderId(orderid);


            if (adressService.chechIfAdressIsAlreadyExisted(adr.getStreet(),
                    adr.getHausNumber(),
                    adr.getCity(),
                    adr.getCountry(),
                    adr.getZip())) {
                Adress adress = adressService.getIdAressThatAlreadyexisted(adr.getStreet(),
                        adr.getHausNumber(),
                        adr.getCity(),
                        adr.getCountry(),
                        adr.getZip());
                orderService.updateOrderAdress(adress, orderid);
                order1.setAdress(adress);
            } else {
                adressService.addNewAdress(adr);
                orderService.updateOrderAdress(adr, orderid);
                order1.setAdress(adr);

            }
            order1.setTotalAmount(orderService.getSumTotalPrice(user1.getId()));
            model.addAttribute("totalprice", order1.getTotalAmount());
            Adress adress = order1.getAdress();
            model.addAttribute("adr", adress);
            LocalDateTime date = order1.getDeliveryDate();
            model.addAttribute("date", date);
            return "confirmation";
        }
    }

    @GetMapping("/confirmed")
    public String confirmed(Principal principal) {
        Customer user1 = userService.findByEmail(principal.getName());
        if (user1 == null) {
            return "redirect:/User/logout";
        } else {
            long orderId = orderService.getOrderIdInBearbeitung(user1.getId());
            Order order1 = orderService.getOrderByOrderId(orderId);

            order1.setStatus(Status.CONFIRMED);
            cartService.deleteAllCartIteamByUserId(user1.getId());
            orderIteamService.deleteOrderIteamAfterConfrmation(user1.getId());

            /*emailService.orderConfirmationEmail(user1, order1.getDeliveryDate());*/
            return "redirect:/Product/Home";
        }
    }

    @GetMapping("/CouponCode")
    public String couponCode(@Param("CouponCode") String CouponCode, RedirectAttributes redirAttrs, Principal principal, Model model) {

        Voucher voucher = orderService.checkIfCodeIsAvailable(CouponCode);
        if (voucher == null) {
            redirAttrs.addFlashAttribute("error", "The coupon code is not available ");
            return "redirect:/Order/viewConfirmedPage";
        } else {
            Customer user1 = userService.findByEmail(principal.getName());
            if (user1 == null) {
                return "redirect:/User/logout";
            } else {
                long orderId = orderService.getOrderIdInBearbeitung(user1.getId());
                Order order1 = orderService.getOrderByOrderId(orderId);

                long newTotalAmount = order1.getTotalAmount() - voucher.getAmount();
                if (newTotalAmount <= 0) {
                    newTotalAmount = 0;
                }
                orderService.updateTotalAmounte(newTotalAmount, orderId);
                orderService.updateVorcherStatus(voucher.getVoucherID());

                model.addAttribute("totalprice", newTotalAmount);
                model.addAttribute("adr", order1.getAdress());
                LocalDateTime date = order1.getDeliveryDate();
                model.addAttribute("date", date);
                return "confirmation";
            }
        }
    }


    @GetMapping("/cancelOrder")
    public String cancelOrder(Principal principal) {

        Customer user1 = userService.findByEmail(principal.getName());
        if (user1 == null) {
            return "redirect:/User/logout";
        } else {
            orderIteamService.deleteOrderIteamAfterConfrmation(user1.getId());
            orderService.deleteOrderByUserId(user1.getId());

            return "redirect:/Product/Home";
        }
    }

    @GetMapping("/DeleteSingleOrderIteam")
    public String deleteSingleiteamFromOrderIteam(@Param("Pid") long Pid, Principal principal) {
        Customer user1 = userService.findByEmail(principal.getName());
        if (user1 == null) {
            return "redirect:/User/logout";
        } else {
            cartService.deleteSingleCartIteam(Pid, user1.getId());
            orderIteamService.deleteAllOrderIteamsTransaktion(orderService.getOrderIdInBearbeitung(user1.getId()));
            orderService.deleteOrderByUserId(user1.getId());
            return "redirect:/Cart/viewCart";
        }
    }
    
    
    @GetMapping("/userorders")
    public String userOrders(  Model model, Principal principal) {
        User user1 = userService.findByEmail(principal.getName());
        List<Order> orderList = orderService.getAllOrdersbyUserId(user1.getId());
/*        for (Order i : orderList)
            System.out.println("List orderViewForUser "+ i.getAdress());*/
        model.addAttribute("orderList", orderList);
        return "orderViewForUser";
    }


    @GetMapping("/processOrder")
    public String processOrder( Model model, Principal principal) {
        User user1 = userService.findByEmail(principal.getName());
        List<Order> allOrders = orderService.getAllConfirmedOrder();
        model.addAttribute("allOrders", allOrders);
        return "orderViewForChef";
    }

    @GetMapping("/ready")
    public String changeToReady( @Param("orderId") long orderId) {
        Order order1 = orderService.getOrderByOrderId(orderId);
        orderService.setOrderStatusToReady(orderId);
        System.out.println("the status is"+order1.getStatus());
        return "redirect:/Order/processOrder";
    }


}
