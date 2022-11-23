package com.musicdistribution.streamingservice.domain.exception;

import com.musicdistribution.streamingservice.constant.ExceptionConstants;
import com.musicdistribution.streamingservice.domain.model.entity.SongId;

/**
 * Runtime exception that is thrown when a song is not found.
 */
public class SongNotFoundException extends RuntimeException {

    /**
     * Public constructor for the exception.
     *
     * @param id - the ID of the song which was not found.
     */
    public SongNotFoundException(SongId id) {
        super(String.format(ExceptionConstants.SONG_NOT_FOUND, id.getId()));
    }
}
