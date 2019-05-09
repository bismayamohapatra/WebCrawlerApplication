package com.ge.digital.service;

import org.json.simple.JSONArray;

import java.util.Map;

/**
 * This is the class for crawling a JSON file to an infinite depth
 * and displays the standard output as below and append to file also
 *  Success:
 *  ["page-99","page-01","page-04","page-05","page-02","page-03","page-08","page-09","page-06","page-07"]
 *  Skipped:
 *  ["page-01","page-10","page-04","page-05","page-02","page-03","page-08","page-09"]
 *  Error:
 *  ["page-11","page-00","page-12","page-10","page-13"].
 *
 *  @author  Bismaya Mohapatra
 * @version 1.0
 * @since   09-05-2019
 */
public class XmlUrlCrawler implements Parser {


    @Override
    public Map<String,JSONArray> parse(String filePath, String startingNode) throws Exception {
        //TODO : Future implementation for XML type
        return null;
    }
}
