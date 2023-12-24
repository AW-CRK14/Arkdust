package com.ardc.arkdust;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
    public static final String MOD_ID = "arkdust";//定义modID
    public static String getLogName(String name) {
        return MOD_ID + "/" + name;
    }

    public static final Logger LOGGER = LogManager.getLogger();
}
