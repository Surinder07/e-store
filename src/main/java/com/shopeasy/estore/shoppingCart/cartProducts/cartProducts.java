package com.shopeasy.estore.shoppingCart.cartProducts;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "cartProducts")
@Entity
@Component
public class cartProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Long productId;

    public int productQuantity = 1;


    public cartProducts(Long productId, int productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;

    }
}
