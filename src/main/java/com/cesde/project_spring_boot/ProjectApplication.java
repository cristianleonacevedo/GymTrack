package com.cesde.project_spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class of the Spring Boot application - CESDE Project
 *
 * @SpringBootApplication: Combines several annotations:
 * - @Configuration: Allows you to configure the application
 * - @EnableAutoConfiguration: Enables automatic configuration
 * - @ComponentScan: Searches for components in this package and sub-packages
 */
@SpringBootApplication
public class ProjectApplication {

  /**
   * Main method - Application entry point
   */
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);

    System.out.println("\n" +
      "====================================\n" +
      "✅ CESDE - API started!\n" +
      "🌐 URL: http://localhost:8080/api/users\n" +
      "📖 Swagger: http://localhost:8080/swagger-ui.html\n" +
      "📚 Educational project - Spring Boot 3.x\n" +
      "===================================="
    );
	}
}
