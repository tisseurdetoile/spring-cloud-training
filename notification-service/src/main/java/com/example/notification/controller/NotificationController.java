package com.example.notification.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.notification.domain.Notification;
import com.example.notification.repository.NotificationRepository;

@Controller
@RequestMapping("/")
public class NotificationController {
	@Autowired
	private NotificationRepository notificationRepository;
	
	@GetMapping
	public ResponseEntity<?> getNotifications() {
		return ResponseEntity.ok(new Resources<>(notificationRepository.findAll().stream()
				.map(Notification::getMessage)
				.collect(Collectors.toList())));
	}
	
	@PostMapping
	public ResponseEntity<?> sendNotification(@RequestBody String message) {
		notificationRepository.save(new Notification(message));
		return ResponseEntity.noContent().build();
	}
}
