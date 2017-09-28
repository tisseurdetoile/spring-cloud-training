package com.example.notification;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface RabbitMqChannels {
	String NOTIFICATION = "notification";

	@Input(NOTIFICATION)
	SubscribableChannel notification();
}
