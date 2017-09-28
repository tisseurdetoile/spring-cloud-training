package com.example.orders;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface RabbitMqChannels {
	String NOTIFICATION = "notification";

	@Output(NOTIFICATION)
	SubscribableChannel notification();
}
