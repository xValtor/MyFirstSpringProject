package com.example.labs;

import com.example.labs.repo.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LabsApplication {


    public static void main(String[] args) {

        SpringApplication.run(LabsApplication.class, args);
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.matches("123456", "$2a$10$ge8VC6rSvFoWmugXnLU8xevu1QNSk9OnJIT6PiS6iWCufuHvZ.x3C"));

    }


}
