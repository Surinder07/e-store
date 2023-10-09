package com.shopeasy.estore.shoppingCart.cartProducts;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@AllArgsConstructor
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

    public Long uId;


    public cartProducts(Long productId,Long uId, int productQuantity) {
        this.productId = productId;
        this.uId=uId;
        this.productQuantity = productQuantity;

    }
}
