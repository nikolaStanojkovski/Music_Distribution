package com.musicdistribution.storageservice.domain.service.implementation;

import com.musicdistribution.storageservice.domain.exception.EncryptionException;
import com.musicdistribution.storageservice.domain.service.IEncryptionSystem;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Optional;

@Component
public class EncryptionSystemService implements IEncryptionSystem {
    private final Cipher cipher;
    private final SecretKey key;

    @Autowired
    public EncryptionSystemService(Environment environment) throws Exception {
        String encryptionKey = Optional.ofNullable(environment
                .getProperty("musicDistribution.app.encryptionKey"))
                .orElse("");
        String encryptionScheme = Optional.ofNullable(environment
                .getProperty("musicDistribution.app.encryptionScheme"))
                .orElse("");

        byte[] arrayBytes = encryptionKey.getBytes(StandardCharsets.UTF_8);
        KeySpec ks = new DESedeKeySpec(arrayBytes);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(encryptionScheme);
        this.cipher = Cipher.getInstance(encryptionScheme);
        this.key = skf.generateSecret(ks);
    }

    @Override
    public String encrypt(String unencryptedString) {
        String encryptedString;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = unencryptedString.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = Base64.encodeBase64URLSafeString(encryptedText);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EncryptionException("Encryption of the string: " + unencryptedString + " has failed");
        }
        return (!unencryptedString.isEmpty()) ? encryptedString : "";
    }

    @Override
    public String decrypt(String encryptedString) {
        String decryptedText;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.decodeBase64URLSafe(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText = new String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EncryptionException("Decryption of the string: " + encryptedString + " has failed");
        }
        return (!encryptedString.isEmpty()) ? decryptedText : "";
    }
}
