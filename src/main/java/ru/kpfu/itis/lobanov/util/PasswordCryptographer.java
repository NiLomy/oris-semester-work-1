package ru.kpfu.itis.lobanov.util;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordCryptographer {
    public static final String ENCRYPTING_ALGORITHM = "MD5";

    public static String encrypt(String password) {
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
// check all inputs: selects, dropbox, et
// сделкть хеширование в отдельном треде его стирать через какое-то время и сохранять в карту сущности хранящие объект и время его получения