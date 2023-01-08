package com.example.rest_villains;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableWebMvc
public class RestVillainsApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestVillainsApplication.class, args);
    }
}
