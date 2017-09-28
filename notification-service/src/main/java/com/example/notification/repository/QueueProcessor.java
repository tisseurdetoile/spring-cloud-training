package com.example.notification.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.stereotype.Component;

import com.example.notification.RabbitMqChannels;

@Component
@MessageEndpoint
public class QueueProcessor {
	private static Logger logger = LoggerFactory.getLogger(QueueProcessor.class);

	@StreamListener(RabbitMqChannels.NOTIFICATION)
	public void onMessage(String message) {
		logger.error(message);
	}
}
