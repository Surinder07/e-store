package com.shopeasy.estore.shoppingCart;

import com.shopeasy.estore.auth.UserNotFoundException;
import com.shopeasy.estore.product.Product;
import com.shopeasy.estore.product.ProductRepository;
import com.shopeasy.estore.security.exception.InsufficientQuantityException;
import com.shopeasy.estore.security.exception.ProductNotFoundException;
import com.shopeasy.estore.shoppingCart.cartProducts.cartProducts;
import com.shopeasy.estore.user.User;
import com.shopeasy.estore.user.UserRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static springfox.documentation.spi.service.contexts.SecurityContext.builder;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private  final UserRepository userRepository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public void addToCart(Long productId, Long userId, int quantity) {
        //User userData = null;

        //Check for user exist or not
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        //Check for product is present or not
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found."));

        //check to see if requested quantity is less than inventory or not.
        if (product.getAvailableQuantity() < quantity) {
            throw new InsufficientQuantityException("Insufficient product quantity.");
        }

        System.out.println("validation passed");
        //Checking if cart is already present for userID or not
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findByUserId(userId);

        //Checking if shopping cart present for userID

        if(shoppingCart.isPresent()){
            ShoppingCart existingCart = shoppingCart.get();

            //update product list
            try {
                //getting existing cart products
                List<cartProducts> existingCartProducts = existingCart.getCart();
                boolean found = false;
                //adding new entry in cart products
                cartProducts tempCart = new cartProducts();

                for (cartProducts cp : existingCartProducts) {
                    if (Objects.equals(cp.getProductId(), productId)) {
                        // Update the quantity if the product ID matches
                        tempCart = cp;
                        quantity = cp.getProductQuantity() + quantity;
                    }
                }

                tempCart.setProductQuantity(quantity);
                existingCartProducts.remove(tempCart);
                existingCartProducts.add(new cartProducts(productId,userId,quantity));

                System.out.println(existingCartProducts);

                existingCart.setCart(existingCartProducts);

                //save cart
                shoppingCartRepository.save(existingCart);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            try {
                //add new cart for user
                cartProducts cart = new cartProducts();
                    cart.setProductId(productId);
                    cart.setUId(userId);
                    cart.setProductQuantity(quantity);
                List<cartProducts> cartProducts = new ArrayList<>();
                    cartProducts.add(cart);
                ShoppingCart cartItem = new ShoppingCart();
                    cartItem.setUser(user);
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
        return false; // Not Found
    }
}
