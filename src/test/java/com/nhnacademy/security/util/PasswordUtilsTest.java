package com.nhnacademy.security.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class PasswordUtilsTest {
    private static final String PASSWORD = "12345";
    private static final String DIGEST = "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5";


    @Test
    public void testSimpleHash() throws Exception {
        List<String> hashes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String digest = PasswordUtils.simple(PASSWORD);

            log.info("{}: digest={}", i, digest);
            hashes.add(digest);
        }

        for (int i = 0; i < 10; i++) {
            assertThat(hashes.get(i)).isEqualTo(DIGEST);
        }
    }

    @Test
    public void testSaltAndIteration() throws Exception {
        List<String> hashes = new ArrayList<>();

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[8];

        for (int i = 0; i < 10; i++) {
            random.nextBytes(salt);
            String digest = PasswordUtils.encode(PASSWORD, salt);

            log.info("{}: salt={}, digest={}", i, PasswordUtils.bytesToHex(salt), digest);
            hashes.add(digest);
        }

        Set<String> set = new HashSet<>(hashes);
        assertThat(set).hasSize(10);
    }

}
