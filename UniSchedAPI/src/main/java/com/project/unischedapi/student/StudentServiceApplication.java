package com.project.unischedapi.student;

import com.project.unischedapi.user.UserServiceApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = {
        "com.project.unischedapi.student",
        "com.project.unischedapi.config"
})
@PropertySource("classpath:application-student.properties")
public class StudentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentServiceApplication.class, args);
    }
}
