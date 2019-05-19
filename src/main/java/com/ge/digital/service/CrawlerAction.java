package com.ge.digital.service;

import com.ge.digital.dto.Page;
import com.ge.digital.main.ParserApplication;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * This is a callable class to process a address and return the Page
 */
public class CrawlerAction implements Callable<Page> {

    private static final Logger logger = Logger.getLogger(CrawlerAction.class);
    private final String address;
    private final Map<String,Page> pageMap;

    public CrawlerAction(String address, final Map<String,Page> pageMap){
        this.address = address;
        this.pageMap = pageMap;
    }

    @Override
    public Page call() {
        logger.debug("Validating page "+ address +" for processing eligibility");
        boolean isProcess  = checkForProcessing(address);
        if(isProcess) {
            return pageMap.get(address);
        }
        return null;
    }

    /**
     * This method will check if the page is not processed and return
     *  accordingly by adding to completed/success/failure list
     * @param address
     * @return boolean
     */
    private boolean checkForProcessing(final String address){
        boolean isProcess = false;
        if(ParserApplication.completed.contains(address)) {
            ParserApplication.skipped.add(address);
        }else {
            if(pageMap.containsKey(address)){
                isProcess = true;
                ParserApplication.completed.add(address);
                ParserApplication.success.add(address);
            }else{
                ParserApplication.completed.add(address);
                ParserApplication.error.add(address);
            }
        }
        return isProcess;
    }
}
