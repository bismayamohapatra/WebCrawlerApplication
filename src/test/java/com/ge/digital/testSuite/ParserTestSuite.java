package com.ge.digital.testSuite;

import com.ge.digital.dto.PageTest;
import com.ge.digital.main.ParserApplicationTest;
import com.ge.digital.service.CrawlerActionTest;
import com.ge.digital.service.JsonUrlCrawlerTest;
import com.ge.digital.utils.ParserUtilityTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//JUnit Suite Test
@RunWith(Suite.class)
@Suite.SuiteClasses({
        JsonUrlCrawlerTest.class,
        PageTest.class,
        ParserUtilityTest.class,
        ParserApplicationTest.class,
        CrawlerActionTest.class
})

public class ParserTestSuite {
}
