package by.training.utility;

import java.security.NoSuchAlgorithmException;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public abstract class SecureData {

    public static String secureBySha(final String rawPass, final String salt)
            throws NoSuchAlgorithmException {
        MessageDigestPasswordEncoder encoder = new ShaPasswordEncoder();
        return encoder.encodePassword(rawPass, salt);
    }

}
