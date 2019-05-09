package com.ge.digital.service;

import java.util.Map;

public interface Parser {

    Map<?,?> parse(final String filePath, final String startingNode) throws Exception;

}
