package com.shopeasy.estore.product;

import lombok.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import jakarta.persistence.*;
import org.springframework.data.redis.core.RedisHash;

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
    private Long id;
    @NotEmpty(message = "Product name is required")
    private String productName;

    @NotNull(message = "Product type is required")
    private String productType;

    @NotNull(message = "Product description is required")
    private String productDescription;

    @Positive(message = "Price must be a positive value")
    @NotNull(message = "Product Price can't be null")
    private double price;
}
