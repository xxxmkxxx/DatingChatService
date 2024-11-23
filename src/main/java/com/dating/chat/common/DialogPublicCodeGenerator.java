package com.dating.chat.common;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class DialogPublicCodeGenerator {
    private final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final SecureRandom random = new SecureRandom();

    public String generate(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(chars.length());
            stringBuilder.append(chars.charAt(randomIndex));
        }

        return stringBuilder.toString();
    }
}
