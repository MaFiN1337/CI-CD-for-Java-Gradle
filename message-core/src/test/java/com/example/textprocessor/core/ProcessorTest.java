package com.example.textprocessor.core;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@Tag("core")
class ProcessorTest {

    @Test
    void testUpperCaseConversion() {
        final MessageProcessor processor = new MessageProcessor();
        final String input = "hello world$";
        final String expected = "hello world";
        assertEquals(expected, processor.processMessage(input).getProcessedText());
    }

    @Test
    void testEmptyMessage() {
        final MessageProcessor processor = new MessageProcessor();
        final String input = "";
        final String expected = "";
        assertEquals(expected, processor.processMessage(input).getProcessedText());
    }

    @Test
    void testAssumptionWithOS() {
        assumeTrue(System.getProperty("os.name").toLowerCase().contains("linux"));
        final MessageProcessor processor = new MessageProcessor();
        final String input = "linux only";
        final String result = processor.processMessage(input).getProcessedText();
        assertNotNull(result);
    }
}
