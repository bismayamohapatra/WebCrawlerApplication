package com.ge.digital.service;

import com.ge.digital.dto.Page;
import com.ge.digital.exception.FileParsingException;
import com.ge.digital.exception.PageNotFoundException;
import com.ge.digital.main.ParserApplication;
import com.ge.digital.utils.ParserUtility;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
public class JsonUrlCrawler implements Parser {

    private static final Logger logger = Logger.getLogger(JsonUrlCrawler.class);
    private static final ResourceBundle bundle = ResourceBundle.getBundle("config");
    private static volatile JsonUrlCrawler instance = null;

    private JSONArray page = new JSONArray();
    private static final ForkJoinPool pool = new ForkJoinPool(Integer.valueOf(bundle.getString("THREAD_POOL_SIZE")));
    public static Map<String,Page> pageMaps= new HashMap<>();

    private JsonUrlCrawler() {
    }

      public static JsonUrlCrawler getInstance() {
            if (instance == null) {
                synchronized (JsonUrlCrawler.class) {
                    if (instance == null) {
                        instance = new JsonUrlCrawler();
                    }
                }
            }
        return instance;
    }
    
    /**
     *
     * @param filePath
     * @param startingPage
     * @return Map<String,JSONArray>
     * @throws IOException
     * @throws PageNotFoundException
     * @throws FileParsingException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Override
    public Map<String,JSONArray> parse(final String filePath,final String startingPage)throws IOException,PageNotFoundException,FileParsingException,InterruptedException, ExecutionException {
        logger.debug("File : " + filePath + "Starting Page : " + startingPage);
        if(ParserUtility.isNullOrEmpty(filePath) || ParserUtility.isNullOrEmpty(startingPage)){
            throw new FileParsingException("Failed to parse the file with provided parameters File Path : " + filePath + " and starting page : " + startingPage);
        }
        //Read the file from the path provided and process
        logger.debug("Reading File : " + filePath);
        try (FileReader reader = new FileReader(filePath)) {
            pageMaps = parseJson(reader);
            if(pageMaps.isEmpty()){
                throw new FileParsingException("JSON file provided don't contain data");
            }
            parsePage(startingPage);
        }catch (IOException e){
            logger.error("Error loading file. ", e);
            throw e;
        }catch (ParseException e){
            logger.error("Error while parsing the provided file " + filePath,e);
        }
        return getParsedResult();
    }

    /**
     *
     * @param address
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws PageNotFoundException
     */
    private void parsePage(String address) throws InterruptedException, ExecutionException, PageNotFoundException {
        CrawlerAction task = new CrawlerAction(address,pageMaps);
        Future<Page> returnFuture = pool.submit(task);
        Page page = returnFuture.get();
        if(page != null) {
            for(final String link : page.getLinks()){
                logger.debug("Creating separate process for processing page : " + link);
                parsePage(link);
            }
        }
    }

    /**
     *
     * @param reader
     * @return Map<String,Page>
     * @throws IOException
     * @throws ParseException
     * @throws PageNotFoundException
     */
    private Map<String,Page> parseJson(FileReader reader) throws IOException, ParseException, PageNotFoundException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
        page = (JSONArray)jsonObject.get(bundle.getString("PAGES"));
        if(page == null){
            throw new PageNotFoundException("Failed to parse the page from  : " + jsonObject);
        }
        return parseJSONValuesForKey(page,bundle.getString("ADDRESS"));
    }

    /**
     *
      * @param jsonArray
     * @param key
     * @return Map<String,Page>
     */
   private static Map<String,Page> parseJSONValuesForKey(JSONArray jsonArray, String key) {
       Map<String,Page> pageMap = new HashMap<>();
       List<JSONObject> jsonObjects = IntStream.range(0, jsonArray.size()).mapToObj(index -> ((JSONObject)jsonArray.get(index))).collect(Collectors.toList());
       for(JSONObject object : jsonObjects) {
           String address = (String) object.get(key);
           List<String> links = (List<String>) object.get(bundle.getString("LINKS"));
           pageMap.put(address,new Page(address,links));
       }
       return pageMap;
    }


    /**
     * This is method to get the processed results of JSON file
     * @return Map<String JSONArray>
     */
    public Map<String,JSONArray> getParsedResult(){
        //LinkedHashMap to keep the order preserved
        Map<String,JSONArray> resultMap = new LinkedHashMap<>();
        JSONArray successJson = new JSONArray();
        JSONArray skipedJson = new JSONArray();
        JSONArray errorJson = new JSONArray();
        successJson.addAll(ParserApplication.success);
        skipedJson.addAll(ParserApplication.skipped);
        errorJson.addAll(ParserApplication.error);

        resultMap.put("Success:",successJson);
        resultMap.put("Skipped:",skipedJson);
        resultMap.put("Error:",errorJson);
        return resultMap;
    }
}
