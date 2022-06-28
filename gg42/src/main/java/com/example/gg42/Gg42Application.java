package com.example.gg42;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:/application.properties")
@SpringBootApplication
public class Gg42Application {

    public static void main(String[] args) {
        SpringApplication.run(Gg42Application.class, args);
    }

}
