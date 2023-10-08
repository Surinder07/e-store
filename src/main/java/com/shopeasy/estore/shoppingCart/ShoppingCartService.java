package com.shopeasy.estore.shoppingCart;

import com.shopeasy.estore.auth.UserNotFoundException;
import com.shopeasy.estore.product.Product;
import com.shopeasy.estore.product.ProductRepository;
import com.shopeasy.estore.security.exception.InsufficientQuantityException;
import com.shopeasy.estore.security.exception.ProductNotFoundException;
import com.shopeasy.estore.user.User;
import com.shopeasy.estore.user.UserRepository;
import com.shopeasy.estore.user.dto.userDto;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

        //Checking if cart is already present for userID or not
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findByUserId(userId);

        if(shoppingCart.isPresent()){
            ShoppingCart existingCart = shoppingCart.get();
            // update the quantity
            int updatedQuantity = existingCart.getQuantity() + quantity;
            existingCart.setQuantity(updatedQuantity);

            //TODO add quantity for every product of cart
//            Product tempProduct = new Product();
//            tempProduct.setProductName(product.getProductName());
//            tempProduct.setPrice(product);

            //update product list
            List<Product> productList = existingCart.getProducts(); // Assuming you have a method to get the list of products in ShoppingCart
            productList.add(product); // Add the new product to the list
            existingCart.setProducts(productList); // Set the updated product list back to the cart

            //save cart
            shoppingCartRepository.save(existingCart);
            System.out.println("existing shopping cart updated.");
        }else{
            try {
                //add new cart for user
                ShoppingCart cartItem = new ShoppingCart();
                cartItem.setUser(user);
                List<Product> productList = new ArrayList<>();
                productList.add(product);
                cartItem.setProducts(productList);
                cartItem.setQuantity(quantity);

                shoppingCartRepository.save(cartItem);
            }catch (Throwable e){
                e.printStackTrace();
            }
        }


        //        try {
        //            userData = new User(User.builder()
        //                    .id(userDto.getId())
        //                    .email(userDto.getEmail())
        //                    .firstname(userDto.getFirstname())
        //                    .lastname(userDto.getLastname())
        //                    .build());
        //        }catch(Throwable e){
        //            e.printStackTrace();
        //        }

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
