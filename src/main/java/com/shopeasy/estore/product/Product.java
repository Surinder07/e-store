package com.shopeasy.estore.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table
public class Product {

    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    @Schema(example = "12332")
    private Long productId;
    @NotEmpty(message = "Product name is required")
    @Schema(example = "Samsung 4k LED TV")
    private String productName;

    @NotNull(message = "Product type is required")
    @Schema(example = "Televisions")
    private String productType;

    @NotNull(message = "Product description is required")
    @Schema(example = "55 inch 4K smart TV")
    private String productDescription;

    @Positive(message = "Price must be a positive value")
    @NotNull(message = "Product Price can't be null")
    @Schema(example = "900.0")
    private double price;
}
