package com.omery.projectAtm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//  exclude = {SecurityAutoConfiguration.class} disables security
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ProjectAtmApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectAtmApplication.class, args);
	}

}