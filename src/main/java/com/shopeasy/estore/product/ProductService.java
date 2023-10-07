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
    public Optional<Product> getProductById(Long id){
        if(id != null){
            return productRepository.findById(id);

        }else{
            throw new ProductNotFoundException("Product Not Found.");
        }
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Optional<Product> deleteProduct(Long id) {
        productRepository.deleteById(id);
        return productRepository.findById(id);
    }


    public Product updateProduct(Product product){
        if(product.getProductId ()==null || product.getProductId ()<=0) {
            throw new ProductNotFoundException("Invalid Product ID");
        }
        Optional<Product> optional = productRepository.findById(product.getProductId ());
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
