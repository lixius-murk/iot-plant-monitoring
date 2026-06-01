package com.example.plantcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


//http://localhost:8080/dashboard.html
@SpringBootApplication
@EnableScheduling
public class PlantCareApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlantCareApplication.class, args);
    }
}