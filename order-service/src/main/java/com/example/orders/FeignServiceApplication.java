package com.example.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FeignServiceApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(FeignServiceApplication.class, args);
	}
}
