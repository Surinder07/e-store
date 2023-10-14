package com.shopeasy.estore.user;

import com.shopeasy.estore.auth.UserNotFoundException;
import com.shopeasy.estore.product.Product;
import com.shopeasy.estore.security.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User userExist(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
    }
}
