package com.musicdistribution.streamingservice.domain.model.response.core;

import com.musicdistribution.streamingservice.domain.model.entity.core.Listener;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Object used for data transfer from the
 * back-end to the front-end for a listener.
 */
@Data
@NoArgsConstructor
public class ListenerResponse {

    private String id;
    private String email;

    private List<SongResponse> favouriteSongs;
    private List<AlbumResponse> favouriteAlbums;
    private List<ArtistResponse> favouriteArtists;

    /**
     * Method used for building a listener response object.
     *
     * @param listener    - the listener entity from which the properties are to be read from.
     * @param encryptedId - the encrypted ID of the listener entity.
     * @return the created response object.
     */
    public static ListenerResponse from(Listener listener, String encryptedId,
                                        List<ArtistResponse> favouriteArtists,
                                        List<AlbumResponse> favouriteAlbums,
                                        List<SongResponse> favouriteSongs) {
        ListenerResponse listenerResponse = new ListenerResponse();
        listenerResponse.setId(encryptedId);
        listenerResponse.setEmail(listener.getUserEmail().getFullAddress());
        if (favouriteArtists != null && !favouriteArtists.isEmpty()) {
            listenerResponse.setFavouriteArtists(favouriteArtists);
        }
        if (favouriteAlbums != null && !favouriteAlbums.isEmpty()) {
            listenerResponse.setFavouriteAlbums(favouriteAlbums);
        }
        if (favouriteSongs != null && !favouriteSongs.isEmpty()) {
            listenerResponse.setFavouriteSongs(favouriteSongs);
        }

        return listenerResponse;
    }
}
