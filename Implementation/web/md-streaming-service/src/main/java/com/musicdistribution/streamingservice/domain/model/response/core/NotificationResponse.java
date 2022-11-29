package com.musicdistribution.streamingservice.domain.model.response.core;

import com.musicdistribution.streamingservice.domain.model.entity.core.Notification;
import com.musicdistribution.streamingservice.domain.model.enums.EntityType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Object used for data transfer from the
 * back-end to the front-end for a notification.
 */
@Data
@NoArgsConstructor
public class NotificationResponse {

    private String listenerId;
    private String publishingId;

    private Boolean isReceived;
    private EntityType type;
    private LocalDateTime publishedTime;
    private LocalDateTime receivedTime;
    private ListenerResponse listenerResponse;

    /**
     * Method used for building a notification response object.
     *
     * @param notification          - the notification entity from which the properties are to be read from.
     * @param encryptedListenerId   - the encrypted ID of the listener entity.
     * @param encryptedPublishingId - the encrypted ID of the publishing entity.
     * @return the created response object.
     */
    public static NotificationResponse from(Notification notification,
                                            String encryptedListenerId,
                                            String encryptedPublishingId) {
        NotificationResponse listenerResponse = new NotificationResponse();
        listenerResponse.setListenerId(encryptedListenerId);
        listenerResponse.setPublishingId(encryptedPublishingId);
        listenerResponse.setIsReceived(notification.getIsReceived());
        listenerResponse.setType(notification.getType());
        listenerResponse.setPublishedTime(notification.getPublishedTime());
        listenerResponse.setReceivedTime(notification.getReceivedTime());
        listenerResponse.setListenerResponse(ListenerResponse.from(notification.getReceiver(), encryptedListenerId));

        return listenerResponse;
    }
}
