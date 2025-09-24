package com.recipes.recipes_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.retry.annotation.EnableRetry;

@EnableAsync
@EnableRetry
@SpringBootApplication
public class RecipesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipesApiApplication.class, args);
	}

}
