package com.nhnacademy.security.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class PasswordUtils {
    private static final int DEFAULT_ITERATIONS = 1024;

    private PasswordUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String simple(String rawPassword) {
        byte[] digest = null;
        try {
            digest = sha256WithoutSaltAndIterations(rawPassword);
        } catch (NoSuchAlgorithmException ex) {
            log.error("", ex);
        }

        return bytesToHex(digest);
    }

    public static String encode(String rawPassword, byte[] salt) {
        return encode(rawPassword, salt, DEFAULT_ITERATIONS);
    }

    public static String encode(String rawPassword, byte[] salt, int iterations) {
        byte[] digest = null;
        try {
            digest = sha256(rawPassword, salt, iterations);
        } catch (NoSuchAlgorithmException ex) {
            log.error("", ex);
        }

        return bytesToHex(digest);
    }

    private static byte[] sha256(String rawPassword, byte[] salt, int iterations) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(salt);

        byte[] input = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
        for (int i = 0; i < iterations; i++) {
            digest.reset();
            input = digest.digest(input);
        }

        return input;
    }

    private static byte[] sha256WithoutSaltAndIterations(String rawPassword) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        return digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b: bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static byte[] hexToBytes(String hex) {
        int len = hex.length();

        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                                  + Character.digit(hex.charAt(i+1), 16));
        }

        return data;
    }

}
