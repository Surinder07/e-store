package com.shopeasy.estore.shoppingCart;

import com.shopeasy.estore.security.exception.InsufficientQuantityException;
import com.shopeasy.estore.security.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam Long productId,@RequestParam Long userId, @RequestParam int quantity) {
        try {
            shoppingCartService.addToCart(productId, userId, quantity);
            return ResponseEntity.ok("Product added to cart successfully.");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        } catch (InsufficientQuantityException e) {
            return ResponseEntity.badRequest().body("Insufficient product quantity.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @GetMapping("/view")
    public ResponseEntity<List<ShoppingCart>> viewShoppingCart() {
        List<ShoppingCart> shoppingCartItems = null;
        try {
            shoppingCartItems = shoppingCartService.getAllShoppingCartItems();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(shoppingCartItems);
    }

    // Endpoint to remove an item from the shopping cart by product ID
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long productId) {
        boolean removed = shoppingCartService.removeFromCart(productId);
        if (removed) {
            return ResponseEntity.ok("Item removed from the cart.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found in the cart.");
        }
    }

    // Other cart-related endpoints (e.g., view cart, remove from cart) can be added here.
}
