package com.musicdistribution.storageservice.domain.exception;

/**
 * Runtime exception that is thrown when an encryption of an object has failed.
 */
public class EncryptionException extends RuntimeException {

    /**
     * Public constructor for the exception.
     *
     * @param message - the message of the error that occurred during encryption.
     */
    public EncryptionException(String message) {
        super(message);
    }
}
