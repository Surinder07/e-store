package com.shopeasy.estore.shoppingcart;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int productId;
    private String productName;

    private int quantity;
    private float amount;

    public ShoppingCart(int productId, String productName, int quantity, float amount) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.amount = amount;
    }

    public ShoppingCart(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

}
