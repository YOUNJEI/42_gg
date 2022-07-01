package com.example.gg42;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@PropertySource("classpath:/application.properties")
@EnableJpaAuditing
@SpringBootApplication
public class Gg42Application {

    public static void main(String[] args) {
        SpringApplication.run(Gg42Application.class, args);
    }

}
