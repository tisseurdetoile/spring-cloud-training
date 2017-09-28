package com.example.orders.controller;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value="NOTIFICATION", fallback=DirectNotificationFallBack.class)
public interface DirectNotificationClient {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> getNotifications();
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> sendNotification(@RequestBody String message);
}
