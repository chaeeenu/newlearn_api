package com.newlearn.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncryptionUtil {

    private EncryptionUtil() {
        throw new IllegalStateException("Do not Create EncryptionUtil Instance");
    }

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encrypt(String input) {
        return encoder.encode(input);
    }

    public static boolean matches(String raw, String encoded) {
        return encoder.matches(raw, encoded);
    }
}
