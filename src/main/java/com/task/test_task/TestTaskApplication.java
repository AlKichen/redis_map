package com.task.test_task;

import com.task.test_task.redis.RedisMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Main application class for demonstrating the functionality of {@link RedisMap}.
 * This class initializes and starts the Spring Boot application.
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class TestTaskApplication {

	/**
	 * The entry point of the Spring Boot application.
	 *
	 * @param args application arguments passed from the command line
	 */
	public static void main(String[] args) {
		SpringApplication.run(TestTaskApplication.class, args);
	}
}
