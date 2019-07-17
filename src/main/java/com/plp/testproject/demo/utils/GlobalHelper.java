package com.plp.testproject.demo.utils;

import org.springframework.stereotype.Component;

@Component
public class GlobalHelper {

    //FTP Serttings
    public static final String FTP_SERVER_NAME = "192.168.1.101";
    public static final Integer FTP_SERVER_PORT = 8082;
    public static final String FTP_USERNAME = "root";
    public static final String FTP_PASSWORD = "root";
    public static final String FTP_PATH = "/report/";

    //CURL Settings
    public static final String CURL_EXE = "C:/Windows/System32/";
    public static final String CURL_SPLIT = " ";
    public static final String X_API_KEY = "63304c70";
    public static final String CURL_LOCATION_PATH = "/location";
    public static final String CURL_LOCATION_FILENAME = "locations.json";
    public static final String CURL_LISTINGSTATUS_PATH = "/listingStatus";
    public static final String CURL_LISTINGSTATUS_FILENAME = "listingstatus.json";
    public static final String CURL_MARKETPLACE_PATH = "/marketplace";
    public static final String CURL_MARKETPLACE_FILENAME = "marketplaces.json";
    public static final String CURL_LISTING_PATH = "/listing";
    public static final String CURL_LISTING_FILENAME = "listings.json";

    //JSON files directory
    public static final String JSON_FILES = "src/main/resources/json/";
    public static final String JSON_FILES_REPORT = "src/main/resources/json/report/";

    //CSV files and directory
    public static final String IMPORTLOG = "importLog.csv";
    public static final String CSV_FILES = "src/main/resources/csv/";

    public static final Long EBAY = 1L;
    public static final Long AMAZON = 2L;

}
