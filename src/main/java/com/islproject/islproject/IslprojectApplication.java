package com.islproject.islproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class IslprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(IslprojectApplication.class, args);
	}

}
