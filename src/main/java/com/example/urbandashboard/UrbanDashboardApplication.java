package com.example.urbandashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class UrbanDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrbanDashboardApplication.class, args);
    }
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//
//        templateEngine.addDialect(new LayoutDialect());
//        return templateEngine;
//    }
}
