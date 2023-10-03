package com.shopeasy.estore.product;

import com.shopeasy.estore.security.exception.InvalidProductException;
import com.shopeasy.estore.security.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @CachePut(cacheNames="Product")
    @CacheEvict(cacheNames = "Product",key="#id",allEntries = true)
    public List<Product> getProductList(){
        System.out.println("Getting Productssssss from Db");return productRepository.findAll();
    }
    @Cacheable(cacheNames = "Product",key="#id")
    public Optional<Product> getProductById(Long id){
        System.out.println("Getting Product from Db");
        if(id != null){
            return productRepository.findById(id);

        }else{
            throw new ProductNotFoundException("Product Not Found.");
        }
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @CacheEvict(cacheNames = "Product",key="#id",allEntries = true)
    public Optional<Product> deleteProduct(Long id) {
        productRepository.deleteById(id);
        return productRepository.findById(id);
    }


    @CachePut(cacheNames = "Product")
    public Product updateProduct(Product product){
        System.out.println("Product Updated");
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

    @CacheEvict(cacheNames = "Product",key="#id",allEntries = true)
    public Product deleteProductById(Long id){
        Product productToBeDeleted = productRepository
                .findById(id)
                .orElseThrow(()->new ProductNotFoundException("product you are trying to delete does not exist"));
        this.productRepository.delete(productToBeDeleted);
        return productToBeDeleted;
    }
}
