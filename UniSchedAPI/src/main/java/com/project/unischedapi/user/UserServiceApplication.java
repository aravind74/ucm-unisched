package com.project.unischedapi.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = {
        "com.project.unischedapi.user",
        "com.project.unischedapi.config"
})
@PropertySource("classpath:application-user.properties")
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}

