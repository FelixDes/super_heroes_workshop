package com.example.rest_hero;

//import com.example.rest_hero.controllers.RouterConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
@SpringBootApplication
public class RestHeroApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestHeroApplication.class, args);
    }
}
