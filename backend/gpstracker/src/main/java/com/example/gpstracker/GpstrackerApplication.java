package com.example.gpstracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class GpstrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GpstrackerApplication.class, args);
	}

}
