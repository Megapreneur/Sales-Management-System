package com.uncledemy.salesmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
//@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true,jsr250Enabled = true)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SalesManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalesManagementSystemApplication.class, args);
    }

}
