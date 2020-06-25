package com.brick.buster.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class BrickBusterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrickBusterApplication.class, args);
    }

}
