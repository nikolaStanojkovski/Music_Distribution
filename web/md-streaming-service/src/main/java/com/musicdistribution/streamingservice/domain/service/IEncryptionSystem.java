package com.musicdistribution.streamingservice.domain.service;

/**
 * A domain service which is used for object encryption.
 */
public interface IEncryptionSystem {

    /**
     * Method used to encrypt a string object.
     *
     * @param unencryptedString - the unencrypted string object.
     * @return - the encrypted string object.
     */
    String encrypt(String unencryptedString);

    /**
     * Method used to decrypt a string object.
     *
     * @param encryptedString - the encrypted string object.
     * @return - the decrypted string object.
     */
    String decrypt(String encryptedString);
}
