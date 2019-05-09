package com.ge.digital.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserUtilityTest {
    @Test
    public void testIsNullOrEmpty_Empty() {
        boolean isEmpty = ParserUtility.isNullOrEmpty("");
        assertEquals(true,isEmpty);
    }

    @Test
    public void testIsNullOrEmpty_NonEmpty() {
        boolean isEmpty = ParserUtility.isNullOrEmpty("Test");
        assertEquals(false,isEmpty);
    }

    @Test
    public void testIsNullOrEmpty_Null() {
        String str = null;
        boolean isEmpty = ParserUtility.isNullOrEmpty(str);
        assertEquals(true,isEmpty);
    }

    @Test
    public void testIsNullOrEmpty_NotNull() {
        String str = "Test1";
        boolean isEmpty = ParserUtility.isNullOrEmpty(str);
        assertEquals(false,isEmpty);
    }

}
