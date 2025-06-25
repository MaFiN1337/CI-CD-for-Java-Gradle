package com.example.textprocessor.utils;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Tag("utils")
class ValidatorTest {

    @Test
    void testValidMessage() {
        final String input = "Hello world";
        assertTrue(MessageValidator.isValid(input));
    }

    @Test
    void testForbiddenWord() {
        final String input = "This is a spam message";
        assertFalse(MessageValidator.containsNoForbiddenWords(input));
    }

    @ParameterizedTest
    @CsvSource({
            "hello, true",
            "free money, true",
            "Get quick promotion, false",
            "no spam here, false"
    })
    void testMultipleScenarios(String input, boolean expected) {
        assertEquals(expected, MessageValidator.containsNoForbiddenWords(input));
    }

    @Test
    void testWithAssumption() {
        assumeTrue(System.getProperty("user.name") != null);
        assertTrue(MessageValidator.isValid("no scam"));
    }
}
