package com.example.notification.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.example.notification.domain.Notification;

public interface NotificationRepository extends Repository<Notification, String> {
	List<Notification> findAll();
	Notification save(Notification notification);
}
