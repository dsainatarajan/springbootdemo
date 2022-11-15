package com.springnewexample.capitalone.SpringDataWebDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringDataWebDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataWebDemoApplication.class, args);
	}

}
