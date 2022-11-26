package com.musicdistribution.streamingservice.domain.model.entity.id;

import com.musicdistribution.sharedkernel.domain.base.DomainObjectId;
import lombok.NoArgsConstructor;

/**
 * ListenerId value object used as the listener identifier.
 */
@NoArgsConstructor
public class ListenerId extends DomainObjectId {

    /**
     * Public constructor used for creating a new listener unique ID.
     *
     * @param uuid - the value of the identifier to be created.
     */
    public ListenerId(String uuid) {
        super(uuid);
    }

    /**
     * Static method for creating a new listener ID.
     *
     * @param uuid - the unique identifier for a listener.
     * @return the created listener ID.
     */
    public static ListenerId of(String uuid) {
        return new ListenerId(uuid);
    }
}
