package com.shopeasy.estore.shoppingCart;

import com.shopeasy.estore.product.Product;
import com.shopeasy.estore.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
//    @Query("SELECT sc FROM ShoppingCart sc WHERE sc.userdto.userId = :userId")
//    Optional<ShoppingCart> findByUserId(Long uid);
    //Optional<ShoppingCart> findByUid(Long id);
}