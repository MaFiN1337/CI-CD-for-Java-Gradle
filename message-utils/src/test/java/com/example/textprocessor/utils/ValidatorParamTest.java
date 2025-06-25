package com.example.textprocessor.utils;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

@Tag("param")
public class ValidatorParamTest {

    @ParameterizedTest
    @CsvSource({
            "hello,true",
            "'',false"
    })
    void testIsNotEmpty(String input, boolean expected) {
        Assumptions.assumeTrue(input != null);
        assertEquals(expected, !input.isEmpty());
    }
}
