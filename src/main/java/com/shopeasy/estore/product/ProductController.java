package com.shopeasy.estore.product;

import com.shopeasy.estore.dto.ErrorDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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





    @Operation(summary = "This request is used to fetch all the products available in the store")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Fetched all products from the store",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class, example = "{productId:12332,productName:Samsung 4K LED TV}"))}),
            @ApiResponse(responseCode = "404",
                    description = "Not Available",
                    content = @Content)
    })
    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts(){
        logger.info("This is an informational log message.");
        logger.error("This is an error log message.");
        return productService.getProductList();
    }



    @ApiOperation(value = "Get a product by ID", response = Product.class)
    @Operation(summary = "This request is used to fetch product available in the store using its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @io.swagger.v3.oas.annotations.media.Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @io.swagger.v3.oas.annotations.media.Content)
    })
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

    @ApiOperation(value = "Add a new product", response = Product.class)
    @Operation(summary = "This request is used to add a new  product to the store database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product added successfully", content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @io.swagger.v3.oas.annotations.media.Content)
    })

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

    @ApiOperation(value = "Update an existing product", response = Product.class)
    @Operation(summary = "This request is used to edit a product using its ID, in the store database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Product.class, example = "{productId:12332,productName:Samsung 4K LED TV}"))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @io.swagger.v3.oas.annotations.media.Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @io.swagger.v3.oas.annotations.media.Content)
    })
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

    @ApiOperation(value = "Delete a product by ID")
    @Operation(summary = "This request is used to delete an existing product using its ID, from the store database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully", content = @io.swagger.v3.oas.annotations.media.Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @io.swagger.v3.oas.annotations.media.Content)
    })
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

