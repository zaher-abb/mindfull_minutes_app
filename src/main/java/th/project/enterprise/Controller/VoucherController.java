package th.project.enterprise.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import th.project.enterprise.Entity.Customer;
import th.project.enterprise.Entity.Product;
import th.project.enterprise.Entity.Voucher;
import th.project.enterprise.Service.VoucherService;

import java.util.List;


@Controller
@RequestMapping("/vouchers")
public class VoucherController {


    @Autowired
    VoucherService voucherService;

    @GetMapping("/all")
    public String displayAllVouchers(Model model) {
        List<Voucher> vouchers = voucherService.findAllVouchers();
        model.addAttribute("vouchers", vouchers);
        return "discounts";
    }


    @GetMapping("/deleteVoucher")
    public String deleteVoucher(@Param("voucherID") long voucherID) {
        voucherService.deleteVoucher(voucherID);
        return "redirect:/vouchers/all";
    }


    @GetMapping("/viewAddDiscountPage")
    public String viewPageaddDiscountFromAdmin(Model model) {
        model.addAttribute("v1", new Voucher());
        return "addVoucher";
    }

    @GetMapping("/AddVoucher")
    public String addVoucher() {

        return "redirect:/vouchers/all";
    }

}



