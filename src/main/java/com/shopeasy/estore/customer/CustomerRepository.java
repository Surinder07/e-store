package com.shopeasy.estore.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    public Customer getCustomerByEmailAndName(String email,String name);
}
