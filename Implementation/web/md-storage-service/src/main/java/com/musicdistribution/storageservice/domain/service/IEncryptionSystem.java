package com.musicdistribution.storageservice.domain.service;

public interface IEncryptionSystem {
    String encrypt(String unencryptedString);
    String decrypt(String encryptedString);
}
