package com.example.notification.controller;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("notification")
@RequestMapping("/")
public interface NotificationClient {

	@GetMapping
	public ResponseEntity<?> getNotifications();
	
	@PostMapping
	public ResponseEntity<?> sendNotification(@RequestBody String message);
}
