package com.musicdistribution.albumcatalog.domain.exceptions;

/**
 * Runtime exception that is thrown when there is an error with the file storage.
 */
public class FileStorageException extends RuntimeException {

    public FileStorageException(String error) {
        super(error);
    }
}