package com.shopeasy.estore.shoppingCart;

import com.shopeasy.estore.product.Product;
import com.shopeasy.estore.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table
public class ShoppingCart {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "product_id", referencedColumnName = "id")
        private Product product;

        @OneToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private User user;

        private int quantity;

}
