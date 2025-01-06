package com.mursalin.SCMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ScmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScmsApplication.class, args);
	}

}
