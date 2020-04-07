package com.quinn.app.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {
    private final static Logger logger = LoggerFactory.getLogger(LogUtils.class);

    public static void info(String code, String message) {
        logger.info("【{}】 {}", code, message);
    }

    public static void error(String code, String message) {
        logger.error("【{}】 {}", code, message);
    }

    public static void debug(String code, String message) {
        logger.debug("【{}】 {}", code, message);
    }

    public static void warn(String code, String message) {
        logger.warn("【{}】 {}", code, message);
    }


}
