package com.example.orders;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FallbackNotificationClient implements NotificationClient {

	@Override
	public ResponseEntity<?> sendNotification(String message) {
		return ResponseEntity.notFound().build();
	}

}
