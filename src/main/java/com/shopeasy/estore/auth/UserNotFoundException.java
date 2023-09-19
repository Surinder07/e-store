package com.shopeasy.estore.auth;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String e) {
        super(e);
    }
}
