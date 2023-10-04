package com.shopeasy.estore.cart;

import com.shopeasy.estore.product.Product;
import com.shopeasy.estore.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public void addToCart(Long productId){
        Product product = productRepository.findById(productId).get();
        if(product !=null){

        }

    }
}
