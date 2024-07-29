package com.example.webshopba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class WebshopBaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebshopBaApplication.class, args);
    }

}
