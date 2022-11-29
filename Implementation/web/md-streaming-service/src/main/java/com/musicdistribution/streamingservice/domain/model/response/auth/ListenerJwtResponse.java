package com.musicdistribution.streamingservice.domain.model.response.auth;

import com.musicdistribution.streamingservice.domain.model.entity.core.Listener;
import com.musicdistribution.streamingservice.domain.model.response.core.ListenerResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Object used to transfer JWT data from the
 * back-end to the front-end for a listener.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ListenerJwtResponse extends JwtResponse {

    private ListenerResponse listenerResponse;

    /**
     * Method used for building a listener response object.
     *
     * @param listener    - the listener entity from which the properties are to be read from.
     * @param jwtToken    - the JWT token used for user authentication.
     * @param encryptedId - the encrypted ID of the listener entity.
     * @return the created response object.
     */
    public static ListenerJwtResponse from(Listener listener, String jwtToken, String encryptedId) {
        ListenerJwtResponse listenerJwtResponse = new ListenerJwtResponse();
        listenerJwtResponse.setListenerResponse(ListenerResponse.from(listener, encryptedId));
        listenerJwtResponse.setJwtToken(jwtToken);

        return listenerJwtResponse;
    }
}
