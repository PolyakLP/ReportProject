package com.plp.testproject.demo.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.plp.testproject.demo.entities.Listings;
import com.plp.testproject.demo.entities.Locations;
import com.plp.testproject.demo.repositories.ListingsRepository;
import com.plp.testproject.demo.repositories.LocationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class CurlHelper {

    private String command = "";
    @Autowired
    private ListingsRepository listingsRepository;
    @Autowired
    private LocationsRepository locationsRepository;

    private List<JsonObject> listingDTOToList;
    private List<JsonObject> locationDTOToList;
    private List<JsonObject> listingStatusDTOList;
    private List<JsonObject> marketPlaceDTOList;

    public List<JsonObject> syncListingList(String key, String path) {
        try {
            listingDTOToList = new ArrayList<>();
            String command = "curl.exe -H \"X-API-Key: " + key + "\" https://my.api.mockaroo.com" + path;
            String pathName = "C:/Windows/System32/";
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.directory(new File(pathName));
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            System.out.println("Reading listings.json...please wait");
            BufferedReader bR = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder responseStrBuilder = new StringBuilder();

            Gson gson = new GsonBuilder().create();
            while ((line = bR.readLine()) != null) {
                JsonParser jsonParser = new JsonParser();
                JsonArray jsonArr = (JsonArray) jsonParser.parse(line);
                for (int i = 0; i < jsonArr.size(); i++) {
                    JsonObject jsonObj = jsonArr.get(i).getAsJsonObject();
                    jsonObj.addProperty("location", jsonArr.get(i).getAsJsonObject().get("location_id").getAsString());
                    jsonObj.remove("location_id");
                    addListingToList(jsonObj);
                }
            }
            inputStream.close();
            System.out.println("Reading listings.json...complete");
            return listingDTOToList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addListingToList(JsonObject json) {
        listingDTOToList.add(json);
    }

    public List<JsonObject> syncLocationList(String key, String path) {
        try {
            locationDTOToList = new ArrayList<>();
            String command = "curl.exe -H \"X-API-Key: " + key + "\" https://my.api.mockaroo.com" + path;
            String pathName = "C:/Windows/System32/";
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.directory(new File(pathName));
            Process process = null;
            process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            System.out.println("Reading locations.json...please wait");
            ObjectMapper mapper = new ObjectMapper();
            BufferedReader bR = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder responseStrBuilder = new StringBuilder();
            while ((line = bR.readLine()) != null) {
                JsonParser jsonParser = new JsonParser();
                JsonArray jsonArr = (JsonArray) jsonParser.parse(line);
                for (int i = 0; i < jsonArr.size(); i++) {
                    JsonObject jsonObj = jsonArr.get(i).getAsJsonObject();
                    addLocationToList(jsonObj);
                }
            }
            inputStream.close();
            System.out.println("Reading locations.json...complete");
            return locationDTOToList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addLocationToList(JsonObject dto) {
        locationDTOToList.add(dto);
    }


    public List<JsonObject> syncListingStatusList(String key, String path) {
        try {
            listingStatusDTOList = new ArrayList<>();
            String command = "curl.exe -H \"X-API-Key: " + key + "\" https://my.api.mockaroo.com" + path;
            String pathName = "C:/Windows/System32/";
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.directory(new File(pathName));
            Process process = null;
            process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            System.out.println("Reading listingstatus.json...please wait");
            ObjectMapper mapper = new ObjectMapper();
            BufferedReader bR = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder responseStrBuilder = new StringBuilder();
            while ((line = bR.readLine()) != null) {
                JsonParser jsonParser = new JsonParser();
                JsonArray jsonArr = (JsonArray) jsonParser.parse(line);
                for (int i = 0; i < jsonArr.size(); i++) {
                    JsonObject jsonObj = jsonArr.get(i).getAsJsonObject();
                    addListingStatusToList(jsonObj);
                }
            }
            inputStream.close();
            System.out.println("Reading listingstatus.json...complete");
            return listingStatusDTOList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addListingStatusToList(JsonObject dto) {
        listingStatusDTOList.add(dto);
    }

    public List<JsonObject> syncMarketPlaceList(String key, String path) {
        try {
            listingStatusDTOList = new ArrayList<>();
            String command = "curl.exe -H \"X-API-Key: " + key + "\" https://my.api.mockaroo.com" + path;
            String pathName = "C:/Windows/System32/";
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.directory(new File(pathName));
            Process process = null;
            process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            System.out.println("Reading marketplaces.json...please wait");
            ObjectMapper mapper = new ObjectMapper();
            BufferedReader bR = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuilder responseStrBuilder = new StringBuilder();
            while ((line = bR.readLine()) != null) {
                JsonParser jsonParser = new JsonParser();
                JsonArray jsonArr = (JsonArray) jsonParser.parse(line);
                for (int i = 0; i < jsonArr.size(); i++) {
                    JsonObject jsonObj = jsonArr.get(i).getAsJsonObject();
                    addListingStatusToList(jsonObj);
                }
            }
            inputStream.close();
            System.out.println("Reading marketplaces.json...complete");
            return listingStatusDTOList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addMarketPlaceToList(JsonObject dto) {
        listingStatusDTOList.add(dto);
    }


}
