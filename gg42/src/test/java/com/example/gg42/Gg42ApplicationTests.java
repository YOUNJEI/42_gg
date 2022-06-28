package com.example.gg42;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:/test.properties")
@SpringBootTest
class Gg42ApplicationTests {

    @Test
    void contextLoads() {
    }

}
