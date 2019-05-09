package com.ge.digital.exception;

import org.apache.log4j.Logger;

public class UnknownFileFormatException extends Exception {

    private static final Logger logger = Logger.getLogger(UnknownFileFormatException.class);
    public UnknownFileFormatException(final String exception){
        super(exception);
        logger.error(exception);
    }
}
