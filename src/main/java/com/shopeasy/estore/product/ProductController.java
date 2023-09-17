package com.shopeasy.estore.product;

import com.shopeasy.estore.dto.ErrorDto;
import com.shopeasy.estore.security.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts(){
        System.out.println("Test for github pull request");
        return productService.getProductList();
    }

    @GetMapping(path = "/getProductById/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProductById(@PathVariable("productId") Long id){
    Optional<Product> product;
    if(id<0){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorDto.builder().message("ID can't be negative")
                        .applicationId("APP1")
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .timestamp(Instant.now()).build()
        );
    }

    try{
        product = Optional.ofNullable(productService.getProductById(id).orElseThrow(() -> new ProductNotFoundException("Product Not Found")));
        return ResponseEntity.status(200).body(product);
    } catch (Exception e){
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorDto.builder().message(e.getMessage())
                        .applicationId("APP1")
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .timestamp(Instant.now()).build()
        );
    }
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
