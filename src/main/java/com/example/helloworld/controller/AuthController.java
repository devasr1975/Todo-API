package com.example.helloworld.controller;

import com.example.helloworld.Repository.UserRepository;
import com.example.helloworld.models.User;
import com.example.helloworld.service.UserService;
import com.example.helloworld.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String ,String > body){
        String email=body.get("email");
        String password=passwordEncoder.encode(body.get("password"));

        if(userRepository.findByEmail(email).isPresent()){
            return  new ResponseEntity<>("Email id already exists", HttpStatus.UNAUTHORIZED);

        }
        userService.createUser(User.builder().email(email).password(password).build());
        return  new ResponseEntity<>("User Created",HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String ,String> body){
        String email=body.get("email");
        String password=body.get("password");

        var userOptional= userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
             return new  ResponseEntity<>("User Not found",HttpStatus.UNAUTHORIZED);
        }
        User user= userOptional.get();
        if(!passwordEncoder.matches(password,user.getPassword())){
            return  new ResponseEntity<>("Invalid user",HttpStatus.UNAUTHORIZED);

        }
        String token =jwtUtil.generateToken(email);
        return new ResponseEntity<>(Map.of("token",token),HttpStatus.OK);
    }


}
