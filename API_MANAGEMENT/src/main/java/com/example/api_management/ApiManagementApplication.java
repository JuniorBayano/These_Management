package com.example.api_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiManagementApplication.class, args);
    }

}
