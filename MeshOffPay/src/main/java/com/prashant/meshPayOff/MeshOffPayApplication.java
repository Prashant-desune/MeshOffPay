package com.prashant.meshPayOff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the offline UPI mesh payment system.
 *
 * Run from terminal:
 *   ./mvnw spring-boot:run        (Linux/Mac)
 *   mvnw.cmd spring-boot:run      (Windows)
 *
 * Then open http://localhost:8080
 */
@SpringBootApplication
public class MeshOffPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MeshOffPayApplication.class, args);
    }
}
