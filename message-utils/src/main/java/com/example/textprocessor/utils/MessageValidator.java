package com.example.textprocessor.utils;

import org.apache.commons.lang3.StringUtils;

public class MessageValidator {

    private static final int MAX_LENGTH = 1000;

    public static boolean isValid(String message) {
        return message != null
                && !StringUtils.isBlank(message)
                && !message.isEmpty()
                && message.length() <= MAX_LENGTH;
    }


    public static boolean containsNoForbiddenWords(String message) {
        if (message == null) return false;

        String[] forbiddenWords = {"spam", "advertisement", "promotion"};
        String lowerMessage = message.toLowerCase();

        for (String word : forbiddenWords) {
            if (lowerMessage.contains(word)) {
                return false;
            }
        }
        return true;
    }

    public static int getEffectiveLength(String message) {
        return message == null ? 0 : StringUtils.deleteWhitespace(message).length();
    }
}