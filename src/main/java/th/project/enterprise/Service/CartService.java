package th.project.enterprise.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.project.enterprise.Entity.Cart;
import th.project.enterprise.Entity.Product;
import th.project.enterprise.Repository.CartRepository;
import th.project.enterprise.Repository.ProductRepository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {


    @Autowired
    CartRepository cartRepository;


    @Autowired
    ProductRepository productRepository;

    public void addToCart(long pid, long uid) {

        Product p1 = productRepository.findById(pid).get();
        if (cartRepository.checkIfIteamAllreadyInCart(pid, uid) >= 1) {
            cartRepository.updateQuantityifIteamAllreayInCart(pid, uid);
        } else {
            Cart cartTempo = new Cart(p1, uid);

            cartRepository.save(cartTempo);
        }
        totalAmount(pid, uid);
    }

    public void updateNumerOfIteams(long pid, int number) {

        cartRepository.updateNumerOfIteams(pid, number);
    }

    public List<Cart> getAllCartIteams(long uid) {

        return cartRepository.findAllCartIteamForOneUser(uid);

    }

    public void deleteAllCartIteamByUserId(long uid) {
        cartRepository.deleteAllCartIteamByUserId(uid);
    }

    public void deleteSingleCartIteam(long Pid, long uid) {
        Cart temp = getSingelCartIteam(Pid, uid);
        if (temp.getQuantity() > 1) {
            temp.setQuantity(temp.getQuantity() - 1);
            temp.setTotalAmountSingleCartIteam(temp.getProduct().getPrice() * temp.getQuantity());
        } else {
            cartRepository.deleteCartIteamByProductId(Pid, uid);

        }
    }

    public int getQuantityByProductId(long pid, long uid) {
        return cartRepository.getQuantityByProductId(pid, uid);
    }


    public void totalAmount(long pid, long uid) {
        Optional<Product> product = productRepository.findById(pid);

        Cart cart = cartRepository.getSingleCartIteam(pid, uid);
        long productPrice = product.get().getPrice();
        int quantity = cartRepository.getQuantityByProductId(pid, uid);
        long total = productPrice * quantity;

        //  cartRepository.setTotalAmountSingleCartIteam(total,cart.getCartID());
        cart.setTotalAmountSingleCartIteam(total);
    }

    public Cart getSingelCartIteam(long pid, long uid) {
        return cartRepository.getSingleCartIteam(pid, uid);
    }


}
