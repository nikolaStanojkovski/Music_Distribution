package com.musicdistribution.streamingservice.domain.model.entity.core;

import com.musicdistribution.sharedkernel.domain.base.AbstractEntity;
import com.musicdistribution.streamingservice.constant.EntityConstants;
import com.musicdistribution.streamingservice.domain.model.entity.id.NotificationId;
import com.musicdistribution.streamingservice.domain.model.enums.EntityType;
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
    private EntityType type;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private Artist creator;

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
     * @param creator      - the creator of the publishing which triggers the notification.
     * @param receiver     - the listener to whom the notification is to be made.
     * @param type         - the type of the notification which is being made.
     * @return the created notification object.
     */
    public static Notification build(String publishingId, Artist creator, Listener receiver, EntityType type) {
        Notification notification = new Notification(receiver.getId().getId(), publishingId);
        notification.isReceived = false;
        notification.publishedTime = LocalDateTime.now();
        notification.receivedTime = null;
        notification.creator = creator;
        notification.receiver = receiver;
        notification.type = type;

        return notification;
    }

    /**
     * Method used for triggering a publish to an already created notification.
     */
    public void trigger() {
        this.isReceived = true;
        this.receivedTime = LocalDateTime.now();
    }
}
