package com.example.Remedicine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class RemedicineApplication {

    public static void main(String[] args) {
        SpringApplication.run(RemedicineApplication.class, args);
    }

}
