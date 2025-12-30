package com.pm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableScheduling
public class ProjectManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementApplication.class, args);
    }
}
