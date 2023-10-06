package com.shopeasy.estore.shoppingcart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Integer> {
}
