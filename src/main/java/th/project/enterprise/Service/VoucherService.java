package th.project.enterprise.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import th.project.enterprise.Entity.Customer;
import th.project.enterprise.Entity.Product;
import th.project.enterprise.Entity.Voucher;
import th.project.enterprise.Repository.AdressRepository;
import th.project.enterprise.Repository.VoucherRepository;

import java.util.List;

@Service
public class VoucherService {

    @Autowired
    VoucherRepository voucherRepository;

    public List<Voucher> findAllVouchers() {
        return voucherRepository.findAllVouchers();
    }

    public void deleteVoucher(Long voucherID) {
        voucherRepository.deleteById(voucherID);
    }


    public void addVourcher(Voucher voucher) {
        voucherRepository.save(voucher);
    }
}
