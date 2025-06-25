package com.example.textprocessor.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.*;

@Tag("basic")
public class ValidatorBasicTest {

    @Test
    void testIsNumeric() {
        assertTrue("123".matches("\\d+"));
    }

    @Test
    void testIsNotNumeric() {
        assertFalse("abc123".matches("\\d+"));
    }
}
