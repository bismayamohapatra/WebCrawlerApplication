package com.ge.digital.exception;

import org.apache.log4j.Logger;

public class FileParsingException extends Exception {

    private static final Logger logger = Logger.getLogger(FileParsingException.class);
    public FileParsingException(final String exception){
        super(exception);
        logger.error(exception);
    }
}
