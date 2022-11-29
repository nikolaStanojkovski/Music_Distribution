package com.musicdistribution.streamingservice.domain.model.response.core;

import com.musicdistribution.streamingservice.domain.model.entity.core.Listener;
import com.musicdistribution.streamingservice.domain.valueobject.core.UserRegistrationInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Object used for data transfer from the
 * back-end to the front-end for a listener.
 */
@Data
@NoArgsConstructor
public class ListenerResponse {

    private String id;
    private String email;
    private UserRegistrationInfo userRegistrationInfo;

    /**
     * Method used for building a listener response object.
     *
     * @param listener      - the listener entity from which the properties are to be read from.
     * @param encryptedId - the encrypted ID of the listener entity.
     * @return the created response object.
     */
    public static ListenerResponse from(Listener listener, String encryptedId) {
        ListenerResponse listenerResponse = new ListenerResponse();
        listenerResponse.setId(encryptedId);
        listenerResponse.setEmail(listener.getUserEmail().getFullAddress());
        listenerResponse.setUserRegistrationInfo(listenerResponse.getUserRegistrationInfo());

        return listenerResponse;
    }
}
