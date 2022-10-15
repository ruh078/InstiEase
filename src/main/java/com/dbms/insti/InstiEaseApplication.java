package com.dbms.insti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class InstiEaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstiEaseApplication.class, args);
		System.out.println("hello");
	}

}