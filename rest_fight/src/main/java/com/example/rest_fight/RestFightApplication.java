package com.example.rest_fight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableFeignClients
//@EnableHystrix
//@EnableHystrixDashboard
public class RestFightApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestFightApplication.class, args);
    }
}
