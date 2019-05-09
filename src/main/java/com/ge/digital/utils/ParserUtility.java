package com.ge.digital.utils;

/**
 * This is an utility class to be used across the Parser utility
 */
public class ParserUtility {

    /**
     *  Private constructor to stop instantiation
     */
    private ParserUtility() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param str
     * @return if null or empty
     */
    public static boolean isNullOrEmpty(final String str){
        return str == null || str.isEmpty();
    }

}
