package com.example.rest_hero;

//import com.example.rest_hero.controllers.RouterConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class RestHeroApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestHeroApplication.class, args);
    }
}
