package com.ge.digital.exception;

import org.apache.log4j.Logger;

public class PageNotFoundException extends Exception {

    private static final Logger logger = Logger.getLogger(PageNotFoundException.class);
    public PageNotFoundException(final String exception){
        super(exception);
        logger.error(exception);
    }
}
