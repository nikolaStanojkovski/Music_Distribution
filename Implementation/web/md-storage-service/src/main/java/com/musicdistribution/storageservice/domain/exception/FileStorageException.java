package com.musicdistribution.storageservice.domain.exception;

/**
 * Runtime exception that is thrown when there is an error with the file storage.
 */
public class FileStorageException extends RuntimeException {

    /**
     * Public constructor for the exception.
     *
     * @param message - the message of the error that occurred when storing a file.
     */
    public FileStorageException(String message) {
        super(message);
    }
}
