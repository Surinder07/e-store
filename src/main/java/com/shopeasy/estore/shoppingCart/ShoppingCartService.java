package com.shopeasy.estore.shoppingCart;

import com.shopeasy.estore.auth.UserNotFoundException;
import com.shopeasy.estore.product.Product;
import com.shopeasy.estore.product.ProductRepository;
import com.shopeasy.estore.product.ProductService;
import com.shopeasy.estore.security.exception.InsufficientQuantityException;
import com.shopeasy.estore.security.exception.ProductNotFoundException;
import com.shopeasy.estore.shoppingCart.cartProducts.cartProducts;
import com.shopeasy.estore.user.User;
import com.shopeasy.estore.user.UserRepository;
import com.shopeasy.estore.user.UserService;
import com.shopeasy.estore.user.userDto.userdto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;
    private  final UserRepository userRepository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public void addToCart(Long productId, Long uId, int quantity) {
        //Check for user exist or not
        User user = userService.userExist(uId);
        ShoppingCart existingCart = null;
        //Check for product is present or not
        Product product = productService.productExist(productId);
        //check to see if requested quantity is less than inventory or not.
        if (product.getAvailableQuantity() < quantity) {
            throw new InsufficientQuantityException("Insufficient product quantity.");
        }
        //Checking if cart is already present for userID or not
        List<ShoppingCart> shoppingCart = shoppingCartRepository.findAll();

        for(ShoppingCart sc :shoppingCart ){
            if(Objects.equals(sc.getUserdto().userId, uId)){
                existingCart = sc;
            }
        }

        //Checking if shopping cart present for given userID
        if(existingCart != null){
            //update product list
            try {
                List<cartProducts> existingCartProducts = existingCart.getCart();
                //adding new entry in cart products
                cartProducts tempCart = new cartProducts();

                // Update the quantity if the product ID matches
                for (cartProducts cp : existingCartProducts) {
                    if (Objects.equals(cp.getProductId(), productId)) {
                        tempCart = cp;
                        quantity = cp.getProductQuantity() + quantity;
                    }
                }
                tempCart.setProductQuantity(quantity);
                existingCartProducts.remove(tempCart);
                existingCartProducts.add(new cartProducts(productId,quantity));
                existingCart.setCart(existingCartProducts);

                //save cart
                shoppingCartRepository.save(existingCart);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            try {
                //add new cart for user
                List<cartProducts> cartProducts = new ArrayList<>();

                cartProducts cart = new cartProducts();
                    cart.setProductId(productId);
                    cart.setProductQuantity(quantity);
                    cartProducts.add(cart);

                userdto userdto = new userdto();
                    userdto.setUserId(uId);
                    userdto.setEmail(user.getEmail());
                    userdto.setFirstname(user.getFirstname());
                    userdto.setLastname(user.getLastname());

                ShoppingCart cartItem = new ShoppingCart();
                    cartItem.setUserdto(userdto);
                    cartItem.setCart(cartProducts);

                shoppingCartRepository.save(cartItem);
            }catch (Throwable e){
                e.printStackTrace();
            }
        }
    }

    public List<ShoppingCart> getAllShoppingCartItems() {
        return shoppingCartRepository.findAll();
    }

    public boolean removeFromCart(Long productId) {
        Optional<ShoppingCart> cartItem = shoppingCartRepository.findById(productId);
        if (cartItem.isPresent()) {
            shoppingCartRepository.deleteById(productId);
            return true; // Item removed
        }
        return false;
    }
}
