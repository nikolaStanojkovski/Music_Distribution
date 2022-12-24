package com.musicdistribution.streamingservice.domain.model.response.core;

import com.musicdistribution.streamingservice.constant.FormatConstants;
import com.musicdistribution.streamingservice.domain.model.entity.core.Notification;
import com.musicdistribution.streamingservice.domain.model.enums.EntityType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;

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
    private String publishedTime;
    private String receivedTime;
    private ArtistResponse creatorResponse;
    private ListenerResponse listenerResponse;

    /**
     * Method used for building a notification response object.
     *
     * @param notification          - the notification entity from which the properties are to be read from.
     * @param encryptedCreatorId    - the encrypted ID of the creator entity.
     * @param encryptedListenerId   - the encrypted ID of the listener entity.
     * @param encryptedPublishingId - the encrypted ID of the publishing entity.
     * @return the created response object.
     */
    public static NotificationResponse from(Notification notification,
                                            String encryptedCreatorId,
                                            String encryptedListenerId,
                                            String encryptedPublishingId) {
        NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setListenerId(encryptedListenerId);
        notificationResponse.setPublishingId(encryptedPublishingId);
        notificationResponse.setIsReceived(notification.getIsReceived());
        notificationResponse.setType(notification.getType());
        notificationResponse.setCreatorResponse(ArtistResponse.from(notification.getCreator(), encryptedCreatorId));
        notificationResponse.setListenerResponse(ListenerResponse.from(notification.getReceiver(),
                encryptedListenerId,
                List.of(), List.of(), List.of()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FormatConstants.DATE_FORMATTER);
        notificationResponse.setPublishedTime((notification.getPublishedTime() != null)
                ? notification.getPublishedTime().format(formatter)
                : StringUtils.EMPTY);
        notificationResponse.setReceivedTime((notification.getReceivedTime() != null)
                ? notification.getReceivedTime().format(formatter)
                : StringUtils.EMPTY);

        return notificationResponse;
    }
}
