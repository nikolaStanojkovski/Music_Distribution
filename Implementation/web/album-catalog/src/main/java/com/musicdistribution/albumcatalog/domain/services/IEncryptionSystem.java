package com.musicdistribution.albumcatalog.domain.services;

public interface IEncryptionSystem {
    String encrypt(String unencryptedString);
    String decrypt(String encryptedString);
}
