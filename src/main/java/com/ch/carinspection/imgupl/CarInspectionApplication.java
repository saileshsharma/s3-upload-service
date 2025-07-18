package com.ch.carinspection.imgupl;

import com.ch.carinspection.CarInspectionEnvApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main Spring Boot application class for Car Inspection.
 * Configures component scanning, JPA entity scanning, and repository scanning.
 * Enables Spring Cloud Config Server.
 */
@SpringBootApplication(scanBasePackages = "com.ch.carinspection")
@EntityScan("com.ch.carinspection.imgupl.model")
@EnableJpaRepositories("com.ch.carinspection.imgupl.service.repository")
@EnableConfigServer  // <-- Add this annotation to enable Config Server
public class CarInspectionApplication {

    public static void main(String[] args) {
        CarInspectionEnvApplication.init(); // <-- loads .env
        SpringApplication.run(CarInspectionApplication.class, args);
    }
}
