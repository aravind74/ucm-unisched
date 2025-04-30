package com.project.unischedapi.appointment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = {
        "com.project.unischedapi.appointment",
        "com.project.unischedapi.config"
})
@PropertySource("classpath:application-appointment.properties")
public class AppointmentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppointmentServiceApplication.class, args);
    }
}
