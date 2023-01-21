package com.musicdistribution.streamingservice.domain.model.entity.id;

import com.musicdistribution.sharedkernel.domain.base.CompositeObjectId;
import lombok.NoArgsConstructor;

/**
 * ListenerId value object used as the listener identifier.
 */
@NoArgsConstructor
public class NotificationId extends CompositeObjectId {

    /**
     * Public constructor used for creating a new receiver notification ID.
     *
     * @param receiverUuid   - the value of the receiver identifier to be created.
     * @param publishingUuid - the value of the publishing (song or album) identifier to be created.
     */
    public NotificationId(String receiverUuid, String publishingUuid) {
        super(receiverUuid, publishingUuid);
    }

    /**
     * Static method used for creating a new notification ID.
     *
     * @param receiverUuid   - the value of the receiver identifier to be created.
     * @param publishingUuid - the value of the publishing (song or album) identifier to be created.
     * @return the created notification ID.
     */
    public static NotificationId of(String receiverUuid, String publishingUuid) {
        return new NotificationId(receiverUuid, publishingUuid);
    }
}
