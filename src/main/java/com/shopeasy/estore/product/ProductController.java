package com.shopeasy.estore.product;

import com.shopeasy.estore.dto.ErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return productService.getProductList();
    }

    @PostMapping("/addProduct")
    public ResponseEntity<?> addNewProduct(@RequestBody Product product) {
        if(product.getProductName().isEmpty() || product.getProductType().isEmpty()
                || product.getProductDescription().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ErrorDto.builder().message("ProductName or Type Can't be empty")
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .timestamp(Instant.now()).build()
            );
        }
        if(product.getPrice() <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ErrorDto.builder().message("Product Price Can't be zero or negative ")
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .timestamp(Instant.now()).build()
            );
        }
        productService.saveProduct(product);
        return ResponseEntity.of(Optional.of(product));
    }


    @DeleteMapping(path = "deleteProduct/{productId}")
    public void deleteProduct(@PathVariable("productId") Long id){
        productService.deleteProduct(id);
    }
}
