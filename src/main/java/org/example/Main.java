package org.example;

import org.example.key_generator.KeyGenerator;

import java.util.List;

public class Main {

    private static final int PUBLIC_KEY_LENGTH = 20;

    public static void main(String[] args) {
        KeyGenerator keyGenerator = new KeyGenerator(PUBLIC_KEY_LENGTH);
        List<Long> publicKey = keyGenerator.generatePublicKey();
        List<Long> privateKey = keyGenerator.generatePrivateKey();

        System.out.printf("Public key: %s, Private key: %s", publicKey, privateKey);
    }
}
