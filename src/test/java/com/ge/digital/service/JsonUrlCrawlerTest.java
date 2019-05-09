package com.ge.digital.service;

import com.ge.digital.dto.Page;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class JsonUrlCrawlerTest {

    private static JsonUrlCrawler urlCrawler;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        JSONArray jsonArray = new JSONArray();
        Class c = Class.forName("com.ge.digital.service.JsonUrlCrawler");
        Method factoryMethod = c.getDeclaredMethod("getInstance");
        urlCrawler = (JsonUrlCrawler) factoryMethod.invoke(null, null);

    }
    @Test
    public void testGetParsedResult() {
        Map<String,JSONArray> result =  urlCrawler.getParsedResult();
        assertNotNull(result);
        assertNotNull(result.entrySet());
        assertEquals(3,result.entrySet().size());
    }

    @Test
    public void testParsePage() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        String input = "Page-01";
        Method method = JsonUrlCrawler.class.getDeclaredMethod("parsePage", String.class);
        method.setAccessible(true);
        method.invoke(urlCrawler, input);
    }

    @Test
    public void testParseJSONValuesForKey() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonArray.add(jsonObject);
        String input = "Page-01";
        Method method = JsonUrlCrawler.class.getDeclaredMethod("parseJSONValuesForKey",JSONArray.class, String.class);
        method.setAccessible(true);
        Object object = method.invoke(urlCrawler, jsonArray,input);
        assertNotNull(object);
    }
}
