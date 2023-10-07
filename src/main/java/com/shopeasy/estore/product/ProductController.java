package com.shopeasy.estore.product;

import com.shopeasy.estore.dto.ErrorDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

import com.shopeasy.estore.security.exception.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
@CrossOrigin("http://localhost:3000/")
public class ProductController {

    @Autowired
    private ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts(){
        logger.info("This is an informational log message.");
        logger.error("This is an error log message.");
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

                        .timestamp(Instant.now()).build());
        }
        try{
            product = Optional.ofNullable(productService.getProductById(id).orElseThrow(() -> new ProductNotFoundException("Product Not Found")));
            return ResponseEntity.status(200).body(product);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        ErrorDto.builder()
                                .message("Product Not found")
                                .applicationId("APP1")
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .timestamp(Instant.now()).build());
        }
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


@PutMapping(path = "/updateProduct/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@PathVariable("productId") Long id, @RequestBody Product product) {
        Optional<Product> oldProd = productService.getProductById (id);

        if (product.getProductName ().isEmpty () || product.getProductType ().isEmpty () || product.getProductDescription ().isEmpty ()) {
            return ResponseEntity.status (HttpStatus.BAD_REQUEST).body (ErrorDto.builder ().message ("ProductName or Type Can't be empty").statusCode (HttpStatus.BAD_REQUEST.value ()).timestamp (Instant.now ()).build ());
        }
        if (product.getPrice () <= 0) {
            return ResponseEntity.status (HttpStatus.BAD_REQUEST).body (ErrorDto.builder ().message ("Product Price Can't be zero or negative ").statusCode (HttpStatus.BAD_REQUEST.value ()).timestamp (Instant.now ()).build ());
        }

        if (oldProd.isPresent ()) {
            Product prod = productService.getProductById (id).get ();
            prod.setPrice (product.getPrice ());
            prod.setProductDescription (product.getProductDescription ());
            prod.setProductName (product.getProductName ());
            prod.setProductType (product.getProductType ());
            productService.saveProduct(prod);
            return ResponseEntity.of (Optional.of (prod));
        }
        else {
            return ResponseEntity.status (HttpStatus.NOT_FOUND)
                    .body (ErrorDto.builder ().message ("Product with id " + id + " was not found")
                    .applicationId("APP1").details ("The product you requested to update was not found in the database")
                    .statusCode (HttpStatus.BAD_REQUEST.value ()).timestamp (Instant.now ()).build ());

        }
    }


    @DeleteMapping(path = "deleteProduct/{productId}")
    public ResponseEntity<Optional<?>> deleteProduct(@PathVariable("productId") Long id) {
        Optional<Product> ProdToBeDeleted = productService.getProductById (id);
        if (ProdToBeDeleted.isPresent ()) {
            ProdToBeDeleted = Optional.ofNullable (productService.deleteProductById (id));
            return ResponseEntity.of (Optional.of (ProdToBeDeleted));
        }
        else {
            return ResponseEntity.status (HttpStatus.NOT_FOUND)
                    .body (Optional.ofNullable (ErrorDto.builder ()
                            .message ("Product with id " + id + " was not found")
                            .applicationId ("APP1").details ("The product you requested to delete was not found in the database")
                            .statusCode (HttpStatus.NOT_FOUND.value ())
                            .timestamp (Instant.now ()).build ()));
        }
    }

}

