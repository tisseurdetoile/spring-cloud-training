package com.example.orders.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.example.orders.RabbitMqChannels;

@Component
public class QueuePublisher {
	@Autowired
	@Qualifier(RabbitMqChannels.NOTIFICATION)
	private SubscribableChannel notification;

	public void publish(String message) { 

		MessageBuilder<String> msg = MessageBuilder.withPayload(message); 
		notification.send(msg.build());
  }
}
