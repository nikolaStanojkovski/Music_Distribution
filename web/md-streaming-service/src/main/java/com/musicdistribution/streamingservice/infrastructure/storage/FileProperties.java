package com.musicdistribution.streamingservice.infrastructure.storage;

import com.musicdistribution.streamingservice.constant.PropertyConstants;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Configuration helper class used for storing static file properties.
 */
@Getter
@Component
public class FileProperties {

    @Value(PropertyConstants.MD_SONG_FILES_LOCATION)
    private String songsLocation;

    @Value(PropertyConstants.MD_SONG_COVER_FILES_LOCATION)
    private String songCoversLocation;

    @Value(PropertyConstants.MD_ALBUM_COVER_FILES_LOCATION)
    private String albumCoversLocation;

    @Value(PropertyConstants.MD_ARTIST_COVER_FILES_LOCATION)
    private String profilePicturesLocation;
}
