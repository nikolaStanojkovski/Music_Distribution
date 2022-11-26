package com.musicdistribution.streamingservice.domain.repository;

import com.musicdistribution.streamingservice.domain.model.entity.Notification;
import com.musicdistribution.streamingservice.domain.model.entity.id.NotificationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for a notification entity.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, NotificationId> {
}
