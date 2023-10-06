package com.shopeasy.estore.security.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String e) {
        super(e);
    }
}
