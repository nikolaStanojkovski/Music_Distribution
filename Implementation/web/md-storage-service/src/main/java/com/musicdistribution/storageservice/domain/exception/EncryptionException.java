package com.musicdistribution.storageservice.domain.exception;

/**
 * Runtime exception that is thrown when an encryption of an object has failed.
 */
public class EncryptionException extends RuntimeException {

    public EncryptionException(String message) {
        super(message);
    }

}
