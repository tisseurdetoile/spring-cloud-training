package com.example.orders;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification", fallback = FallbackNotificationClient.class)
public interface NotificationClient {
	
    @PostMapping("/")
    ResponseEntity<?> sendNotification(@RequestBody String message);

}
