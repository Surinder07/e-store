package com.shopeasy.estore.cart;

import com.shopeasy.estore.product.Product;
import com.shopeasy.estore.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cartId;
    @OneToOne
    private Product product;
    @OneToOne
    private User user;

}
