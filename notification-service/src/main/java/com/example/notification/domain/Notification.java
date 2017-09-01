package com.example.notification.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "notifications", timeToLive = 10 * 1000L)
public class Notification {
	@Id
	private String id;
	
	private String message;
	
	public Notification(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
