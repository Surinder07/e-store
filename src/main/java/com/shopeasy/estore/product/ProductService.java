package com.shopeasy.estore.product;

import com.shopeasy.estore.security.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductRepository productRepository;
    public List<Product> getProductList(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id){
        logger.info("This is a test log message");
        if(id != null){
            return productRepository.findById(id);
        }else{
            throw new ProductNotFoundException("Product Not Found.");
        }
    }

    public void saveProduct(Product employee) {
        logger.info("This is a test log message");
        productRepository.save(employee);
    }

    public void deleteProduct(Long id) {
        logger.info("This is a test log message");
        if(id != null && productRepository.findById(id) != null) {
            productRepository.deleteById(id);
        }else{
            throw new ProductNotFoundException("Product Not Found or Could not be deleted.");
        }
    }
}
