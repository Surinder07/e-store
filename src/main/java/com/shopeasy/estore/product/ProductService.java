package com.shopeasy.estore.product;

import com.shopeasy.estore.security.exception.InvalidProductException;
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

    public void saveProduct(Product employee) {
        productRepository.save(employee);
    }

    public Optional<Product> deleteProduct(Long id) {
        productRepository.deleteById(id);
        return productRepository.findById(id);
    }


    public Product updateProduct(Product product){
        if(product.getId()==null || product.getId()<=0) {
            throw new ProductNotFoundException("Invalid Product ID");
        }
        Optional<Product> optional = productRepository.findById(product.getId());
        Product prod = optional.orElseThrow(()->new ProductNotFoundException("Invalid Customer ID"));
        if(product.getProductName().isEmpty()  || product.getProductDescription().isEmpty() || product.getProductType().isEmpty()) {
            throw new InvalidProductException("Please fill in all the values for updating the product");
        }
        return this.productRepository.save(product);
    }

    public Product deleteProductById(Long id){
        Product productToBeDeleted = productRepository
                .findById(id)
                .orElseThrow(()->new ProductNotFoundException("product you are trying to delete does not exist"));
        this.productRepository.delete(productToBeDeleted);
        return productToBeDeleted;
    }
}
