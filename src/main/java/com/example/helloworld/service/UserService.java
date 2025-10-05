package com.example.helloworld.service;

import com.example.helloworld.Repository.UserRepository;
import com.example.helloworld.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void createUser(User user){
        userRepository.save(user);

    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }
}
