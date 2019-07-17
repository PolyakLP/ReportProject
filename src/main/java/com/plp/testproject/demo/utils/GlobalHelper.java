package com.plp.testproject.demo.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GlobalHelper {

    //FTP Serttings
    protected static final String FTP_SERVER_NAME = "192.168.1.101";
    protected static final Integer FTP_SERVER_PORT = 8082;
    protected static final String FTP_USERNAME = "root";
    protected static final String FTP_PASSWORD = "root";
    protected static final String FTP_PATH = "/report/";

    //CURL Settings
    protected static final String CURL_EXE = "C:/Windows/System32/";
    protected static final String CURL_SPLIT = " ";
    protected static final String X_API_KEY = "63304c70";
    protected static final String CURL_LOCATION_PATH = "/location";
    protected static final String CURL_LOCATION_FILENAME = "locations.json";
    protected static final String CURL_LISTINGSTATUS_PATH = "/listingStatus";
    protected static final String CURL_LISTINGSTATUS_FILENAME = "listingstatus.json";
    protected static final String CURL_MARKETPLACE_PATH = "/marketplace";
    protected static final String CURL_MARKETPLACE_FILENAME = "marketplaces.json";
    protected static final String CURL_LISTING_PATH = "/listing";
    protected static final String CURL_LISTING_FILENAME = "listings.json";

    //JSON files directory
    protected static final String JSON_FILES = "src/main/resources/json/";
    protected static final String JSON_FILES_REPORT = "src/main/resources/json/report/";

    //CSV files and directory
    protected static final String IMPORTLOG = "importLog.csv";
    protected static final String CSV_FILES = "src/main/resources/csv/";

    protected static final Long EBAY = 1L;
    protected static final Long AMAZON = 2L;

}
