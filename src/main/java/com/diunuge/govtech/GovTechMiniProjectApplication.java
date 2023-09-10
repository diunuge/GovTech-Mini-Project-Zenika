package com.diunuge.govtech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class GovTechMiniProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(GovTechMiniProjectApplication.class, args);
	}

	@GetMapping
	public String getHello(){
		return "Hello";
	}
}
