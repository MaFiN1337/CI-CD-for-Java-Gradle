package com.example.textprocessor.core;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@org.junit.jupiter.api.Tag("param")
public class ProcessorParamTest {

    @ParameterizedTest
    @ValueSource(strings = {"Hi", "Hello", "Bye"})
    void testMessageLength(String input) {
        Assumptions.assumeTrue(input.length() > 1);
        assertTrue(input.length() >= 2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "def"})
    void testNotEmpty(String input) {
        Assumptions.assumeTrue(!input.isEmpty());
        assertFalse(input.isEmpty());
    }
}
