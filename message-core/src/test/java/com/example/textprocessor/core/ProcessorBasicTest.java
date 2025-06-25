package com.example.textprocessor.core;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Tag("basic")
public class ProcessorBasicTest {

    @Test
    void testTrimMessage() {
        final String input = "   Hello   ";
        final String output = input.trim();
        assertEquals("Hello", output);
    }

    @Test
    void testEmptyMessage() {
        final String message = "";
        assertTrue(message.isEmpty());
    }
}
