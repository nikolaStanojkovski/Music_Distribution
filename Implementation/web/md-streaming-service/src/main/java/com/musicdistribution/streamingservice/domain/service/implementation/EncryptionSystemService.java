package com.musicdistribution.streamingservice.domain.service.implementation;

import com.musicdistribution.streamingservice.constant.ExceptionConstants;
import com.musicdistribution.streamingservice.constant.PropertyConstants;
import com.musicdistribution.streamingservice.domain.exception.EncryptionException;
import com.musicdistribution.streamingservice.domain.service.IEncryptionSystem;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Optional;

/**
 * The implementation of the domain service used for object encryption.
 */
@Service
@AllArgsConstructor
public class EncryptionSystemService implements IEncryptionSystem {

    private final Cipher cipher;

    private final SecretKey key;

    /**
     * An autowired constructor in order to inject the needed encryption schemes.
     *
     * @param environment - a reference to the local environment of the framework.
     * @throws Exception if the encryption schemes were not instantiated properly.
     */
    @Autowired
    public EncryptionSystemService(Environment environment) throws Exception {
        String encryptionKey = Optional.ofNullable(environment
                .getProperty(PropertyConstants.MD_ENCRYPTION_KEY))
                .orElse(StringUtils.EMPTY);
        String encryptionScheme = Optional.ofNullable(environment
                .getProperty(PropertyConstants.MD_ENCRYPTION_SCHEME))
                .orElse(StringUtils.EMPTY);

        byte[] arrayBytes = encryptionKey.getBytes(StandardCharsets.UTF_8);
        KeySpec ks = new DESedeKeySpec(arrayBytes);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(encryptionScheme);
        this.cipher = Cipher.getInstance(encryptionScheme);
        this.key = skf.generateSecret(ks);
    }

    /**
     * Method used to encrypt a string object.
     *
     * @param unencryptedString - the unencrypted string object.
     * @return - the encrypted string object.
     */
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
            throw new EncryptionException(String.format(ExceptionConstants.STRING_ENCRYPTION_FAILURE, unencryptedString));
        }
        return (!unencryptedString.isEmpty()) ? encryptedString : StringUtils.EMPTY;
    }

    /**
     * Method used to decrypt a string object.
     *
     * @param encryptedString - the encrypted string object.
     * @return - the decrypted string object.
     */
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
            throw new EncryptionException(String.format(ExceptionConstants.STRING_DECRYPTION_FAILURE, encryptedString));
        }
        return (!encryptedString.isEmpty()) ? decryptedText : StringUtils.EMPTY;
    }
}
