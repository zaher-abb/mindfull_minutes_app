package th.project.enterprise.Repository;

import org.springframework.data.jpa.repository.Modifying;
import th.project.enterprise.Entity.Customer;
import th.project.enterprise.Entity.Voucher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VoucherRepository extends CrudRepository<Voucher, Long> {


    @Query("select count(v) from Voucher v where v.pin=:couponCode and v.available <> 0")
    int checkIfCodeIsAvailable(String couponCode);

    @Query("select v from Voucher v where v.pin=:couponCode and v.available <> 0")
    Voucher getVoucherByCouponCode(String couponCode);

    @Modifying
    @Query("update Voucher v set v.available=0 where v.voucherID=:vid")
    void updateVorcherStatus(long vid);

    @Query("SELECT v FROM Voucher v")
    List<Voucher> findAllVouchers();

}
