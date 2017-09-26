package com.example.edge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class EdgeServiceApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(EdgeServiceApplication.class, args);
	}

	
	
}
