package com.example.orders.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class DirectNotificationFallBack implements DirectNotificationClient {
	private static Logger logger = LoggerFactory.getLogger(DirectNotificationFallBack.class);
	
	
	@Override
	public ResponseEntity<?> getNotifications() {
		logger.error ("getNotififcation en erreur");
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<?> sendNotification(String message) {
		logger.error ("sendNotification en erreur");
		// TODO Auto-generated method stub
		return ResponseEntity.ok().build();
	}

}
