package com.project.unischedapi.department;

import com.project.unischedapi.user.UserServiceApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = {
        "com.project.unischedapi.department",
        "com.project.unischedapi.config"
})
@PropertySource("classpath:application-department.properties")
public class DepartmentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DepartmentServiceApplication.class, args);
    }
}
