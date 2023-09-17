package com.shopeasy.estore.product;

import com.shopeasy.estore.security.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    public List<Product> getProductList(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id){
        if(id != null){
            return productRepository.findById(id);
        }else{
            throw new ProductNotFoundException("Product Not Found.");
        }
    }

    public void saveProduct(Product employee) {
        productRepository.save(employee);
    }

    public void deleteProduct(Long id) {
        if(id != null && productRepository.findById(id) != null) {
            productRepository.deleteById(id);
        }else{
            throw new ProductNotFoundException("Product Not Found or Could not be deleted.");
        }
    }
}
