package com.shopeasy.estore.orders;

import com.shopeasy.estore.product.Product;
import com.shopeasy.estore.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double totalAmount;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Product> products;

}