package ru.kpfu.itis.lobanov.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PasswordCryptographer {
    public static final String ENCRYPTING_ALGORITHM = "MD5";

    public String encrypt(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(ENCRYPTING_ALGORITHM);
            md.update(password.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
