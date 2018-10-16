package com.exam.www.testLog;

import org.apache.log4j.Logger;

public class HelloWorld {
    private static final Logger logger = Logger.getLogger(HelloWorld.class);

    public static void main(String[] args) {
        logger.error("访问了HelloWorld");
        logger.warn("访问了HelloWorld");
        logger.info("访问了HelloWorld");
//        logger.debug("访问了HelloWorld");
        System.out.println("main方法");
    }
}
