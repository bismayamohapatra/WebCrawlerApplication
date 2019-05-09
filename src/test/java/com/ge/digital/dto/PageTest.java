package com.ge.digital.dto;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PageTest {

    @Test
    public void testPage() {
        Page page = new Page();
        assertNotNull(page);
        assertNotNull(page.getLinks());
        assertNotNull(page.getAddress());
    }
    @Test
    public void testParametrizedPage() {
        List<String> list = new ArrayList<>();
        list.add("link1");
        list.add("link2");
        Page page = new Page("address",list);
        assertNotNull(page);
        assertNotNull(page.getLinks());
        assertNotNull(page.getAddress());
        assertEquals(2,page.getLinks().size());
    }
}
