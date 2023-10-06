package com.shopeasy.estore.product;

import com.shopeasy.estore.security.exception.InvalidProductException;
import com.shopeasy.estore.security.exception.ProductNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private static final Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;
    @Cacheable(cacheNames="Product")
    public List<Product> getProductList(){
        logger.info("ProductService called GetAllProducts from DB ");
        return productRepository.findAll();
    }
    @Cacheable(cacheNames = "Product",key="#id")
    public Optional<Product> getProductById(Long id){
        logger.info("ProductService getting Product from Db "+id);
        if(id != null){
            return productRepository.findById(id);

        }else{
            logger.error("ProductService Product -NOT-FOUND in Db ");
            throw new ProductNotFoundException("Product Not Found.");
        }
    }

    public void saveProduct(Product product) {
        logger.info("ProductService saving Product "+product.getId()+" in DB");
        productRepository.save(product);
    }

    @CacheEvict(cacheNames = "Product",key="#id",allEntries = true)
    public Optional<Product> deleteProduct(Long id) {
        productRepository.deleteById(id);
        return productRepository.findById(id);
    }


    @CachePut(cacheNames = "Product")
    public Product updateProduct(Product product){
        logger.info("ProductService Called UpdateProduct in Db..");
        if(product.getId()==null || product.getId()<=0) {
            logger.error("ProductService - BAD-REQUEST - INVALID ID");
            throw new ProductNotFoundException("Invalid Product ID");
        }
        Optional<Product> optional = productRepository.findById(product.getId());
        Product prod = optional.orElseThrow(()->new ProductNotFoundException("Invalid Customer ID"));
        if(product.getProductName().isEmpty()  || product.getProductDescription().isEmpty() || product.getProductType().isEmpty()) {
            logger.error("ProductService - BAD-REQUEST - Name|Type|Desc - Empty");
            throw new InvalidProductException("Please fill in all the values for updating the product");
        }
        return this.productRepository.save(product);
    }

    @CacheEvict(cacheNames = "Product",key="#id",allEntries = true)
    public Product deleteProductById(Long id){
        logger.info("ProductService Called deletedProductById from Db" +id);
        Product productToBeDeleted = productRepository
                .findById(id)
                .orElseThrow(()->new ProductNotFoundException("product you are trying to delete does not exist"));
        this.productRepository.delete(productToBeDeleted);
        return productToBeDeleted;
    }
}
