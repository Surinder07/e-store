package com.shopeasy.estore.product;

import com.shopeasy.estore.dto.ErrorDto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

import com.shopeasy.estore.security.exception.ProductNotFoundException;
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
public class ProductController {

    @Autowired
    private ProductService productService;
    private static final Logger logger = LogManager.getLogger(ProductService.class);

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts(){
        logger.info("This is ProductController calling - GetAllProducts");
        return productService.getProductList();
    }

    @GetMapping(path = "/getProductById/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProductById(@PathVariable("productId") Long id){
        logger.info("This is ProductController calling - GetProductById "+id);
    Optional<Product> product;

        if(id<0){
            logger.info("ProductController calling - GetProductById on -ve ID");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorDto.builder().message("ID can't be negative")
                        .applicationId("APP1")
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .timestamp(Instant.now()).build());
        }
        try{
            logger.info("ProductController calling ProductService - GetProductById");
            product = Optional.ofNullable(productService.getProductById(id).orElseThrow(() -> new ProductNotFoundException("Product Not Found")));
            return ResponseEntity.status(200).body(product);
        }catch (Exception e){
            logger.error("ProductController Product- "+id+" NOT-FOUND");
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
        logger.info("ProductController adding Product in DB");
        if(product.getProductName().isEmpty() || product.getProductType().isEmpty()
                || product.getProductDescription().isEmpty()){
            logger.error("ProductController adding Product in DB - BAD REQUEST - Name|Type|Desc - Empty ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ErrorDto.builder().message("ProductName or Type Can't be empty")
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .timestamp(Instant.now()).build()
            );
        }
        if(product.getPrice() <= 0){
            logger.error("ProductController adding Product in DB - BAD REQUEST - Price -ve");
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
        logger.info("ProductController updating Product "+id+" in DB");

        Optional<Product> oldProd = productService.getProductById(id);

        if (product.getProductName().isEmpty() || product.getProductType().isEmpty() || product.getProductDescription().isEmpty ()) {
            logger.error("ProductController updating Product in DB - BAD REQUEST - Name|Type|Desc - Empty ");
            return ResponseEntity.status (HttpStatus.BAD_REQUEST).body (ErrorDto.builder ().message ("ProductName or Type Can't be empty").statusCode (HttpStatus.BAD_REQUEST.value ()).timestamp (Instant.now ()).build ());
        }
        if (product.getPrice () <= 0) {
            logger.error("ProductController adding Product in DB - BAD REQUEST - Price -ve");
            return ResponseEntity.status (HttpStatus.BAD_REQUEST).body (ErrorDto.builder ().message ("Product Price Can't be zero or negative ").statusCode (HttpStatus.BAD_REQUEST.value ()).timestamp (Instant.now ()).build ());
        }

        if (oldProd.isPresent ()) {
            logger.info("ProductController - Product Present- Updating Product");
            Product prod = productService.getProductById (id).get();
            prod.setPrice (product.getPrice ());
            prod.setProductDescription (product.getProductDescription ());
            prod.setProductName (product.getProductName ());
            prod.setProductType (product.getProductType ());
            productService.saveProduct(prod);
            return ResponseEntity.of (Optional.of (prod));
        }
        else {
            logger.error("ProductController Product "+id+" NOT-FOUND");
            return ResponseEntity.status (HttpStatus.NOT_FOUND)
                    .body (ErrorDto.builder ().message ("Product with id " + id + " was not found")
                    .applicationId("APP1").details ("The product you requested to update was not found in the database")
                    .statusCode (HttpStatus.BAD_REQUEST.value ()).timestamp (Instant.now ()).build ());

        }
    }


    @DeleteMapping(path = "deleteProduct/{productId}")
    public ResponseEntity<Optional<?>> deleteProduct(@PathVariable("productId") Long id) {
        logger.info("ProductController Deleting Product "+id);
        Optional<Product> ProdToBeDeleted = productService.getProductById (id);
        if (ProdToBeDeleted.isPresent ()) {
            logger.info("ProductController Deleting Product "+id+" Present");
            ProdToBeDeleted = Optional.ofNullable (productService.deleteProductById (id));
            return ResponseEntity.of (Optional.of (ProdToBeDeleted));
        }
        else {
            logger.error("ProductController Deleting Product "+id+" - NOT-FOUND");
            return ResponseEntity.status (HttpStatus.NOT_FOUND)
                    .body (Optional.ofNullable (ErrorDto.builder ()
                            .message ("Product with id " + id + " was not found")
                            .applicationId ("APP1").details ("The product you requested to delete was not found in the database")
                            .statusCode (HttpStatus.NOT_FOUND.value ())
                            .timestamp (Instant.now ()).build ()));
        }
    }

}

