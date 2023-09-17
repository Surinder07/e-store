package com.shopeasy.estore.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts(){
        return productService.getProductList();
    }

    @PostMapping("/addProduct")
    public void addNewProduct(@RequestBody Product product){
        productService.saveProduct(product);
    }

    @DeleteMapping(path = "deleteProduct/{productId}")
    public void deleteProduct(@PathVariable("productId") Long id){
        productService.deleteProduct(id);
    }
}
