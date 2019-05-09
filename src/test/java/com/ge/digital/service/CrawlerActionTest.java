package com.ge.digital.service;

import com.ge.digital.dto.Page;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CrawlerActionTest {

    private static CrawlerAction crawlerAction;
    private static final String address="Page-01";
    private static final Map<String,Page> pageMap = new HashMap<>();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        pageMap.put(address,new Page());
        crawlerAction = new CrawlerAction(address,pageMap);
    }

    @Test
    public void checkForProcessingTest() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        Method method = CrawlerAction.class.getDeclaredMethod("checkForProcessing", String.class);
        method.setAccessible(true);
        method.invoke(crawlerAction, address);

    }
}
