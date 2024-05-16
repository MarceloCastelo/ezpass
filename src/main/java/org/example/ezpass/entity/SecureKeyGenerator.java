package org.example.ezpass.entity;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecureKeyGenerator {

    private static final Logger logger = Logger.getLogger(SecureKeyGenerator.class.getName());

    public static String generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = new SecureRandom();
            keyGen.init(256, secureRandom);
            byte[] key = keyGen.generateKey().getEncoded();
            return Base64.getEncoder().encodeToString(key);
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "Error generating secure key", e);
        }
        return null;
    }
}
