package com.musicdistribution.streamingservice.domain.model.entity;

import com.musicdistribution.sharedkernel.domain.base.AbstractEntity;
import com.musicdistribution.streamingservice.constant.EntityConstants;
import com.musicdistribution.streamingservice.domain.model.entity.id.NotificationId;
import com.musicdistribution.streamingservice.domain.model.enums.NotificationType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Notification domain entity.
 */
@Entity
@Getter
@Table(name = EntityConstants.NOTIFICATION)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends AbstractEntity<NotificationId> implements Serializable {

    private Boolean isReceived;

    private LocalDateTime publishedTime;

    private LocalDateTime receivedTime;

    @Enumerated(value = EnumType.STRING)
    private NotificationType type;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private Listener receiver;

    /**
     * Protected no args constructor used for creating a new notification entity.
     */
    protected Notification(String receiverId, String publishingId) {
        super(NotificationId.of(receiverId, publishingId));
    }

    /**
     * Method used for creating a new album information object.
     *
     * @param publishingId - the ID of the publishing for which a notification is being created.
     * @param receiver     - the listener to whom the notification is to be made.
     * @param type         - the type of the notification which is being made.
     * @return the created notification object.
     */
    public static Notification build(String publishingId, Listener receiver, NotificationType type) {
        Notification notification = new Notification(receiver.getId().getId(), publishingId);
        notification.isReceived = false;
        notification.publishedTime = LocalDateTime.now();
        notification.receivedTime = null;
        notification.receiver = receiver;
        notification.type = type;

        return notification;
    }
}
