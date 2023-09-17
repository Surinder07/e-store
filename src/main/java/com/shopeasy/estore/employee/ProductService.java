package com.shopeasy.estore.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
