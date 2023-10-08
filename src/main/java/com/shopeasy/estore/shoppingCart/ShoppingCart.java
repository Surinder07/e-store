package com.shopeasy.estore.shoppingCart;

import com.shopeasy.estore.product.Product;
import com.shopeasy.estore.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

//        @OneToMany(fetch = FetchType.EAGER)
//        @JoinColumn(name = "product_id", referencedColumnName = "id")
//        private ArrayList<Product> product;

        @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Product> products = new ArrayList<>();


        @OneToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private User user;

        private int quantity;

}
