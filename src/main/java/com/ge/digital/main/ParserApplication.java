package com.ge.digital.main;

import com.ge.digital.exception.FileParsingException;
import com.ge.digital.exception.PageNotFoundException;
import com.ge.digital.service.Parser;
import com.sun.xml.internal.ws.util.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This is the main class for calling the crawling a JSON file to an infinite depth
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
 * @since   04-05-2019
 */
public class ParserApplication {

    private static final Logger logger = Logger.getLogger(ParserApplication.class);
    private static final ResourceBundle bundle = ResourceBundle.getBundle("config");

    public static volatile Set<String> completed = Collections.newSetFromMap(new ConcurrentHashMap<>());
    public static volatile Set<String> success = Collections.newSetFromMap(new ConcurrentHashMap<>());
    public static volatile Set<String> skipped = Collections.newSetFromMap(new ConcurrentHashMap<>());
    public static volatile Set<String> error = Collections.newSetFromMap(new ConcurrentHashMap<>());


    public static void main(String args[]) throws Exception {
        logger.info("Parsing file: \"" + bundle.getString("FILE_NAME") + "\" ,Starting from Node: \"" + bundle.getString("STARTING_PAGE") + "\"\n");
        String fileExtension = FilenameUtils.getExtension(bundle.getString("FILE_NAME"));
        String className = "com.ge.digital.service." + StringUtils.capitalize(fileExtension) + "UrlCrawler";
        Parser parser;
        try {
            Class c = Class.forName(className);
            Method factoryMethod = c.getDeclaredMethod("getInstance");
            parser = (Parser) factoryMethod.invoke(null, null);
            //parser = (Parser) Class.forName(className).newInstance();
        }catch (ClassNotFoundException e){
            logger.error("Implementation is not present for provided file type parsing. Please provide files with .Json/.Xml/.Yml");
            throw e;
        }
        try {
            Map<String, JSONArray> results = (Map<String, JSONArray>) parser.parse(bundle.getString("FILE_NAME"), bundle.getString("STARTING_PAGE"));
            results.forEach((k, v) -> System.out.println((k + "\n" + v + "\n")));
        }catch (IOException |PageNotFoundException | FileParsingException e){
            logger.error("Error Occurred in Process ", e);
        }
    }
}
