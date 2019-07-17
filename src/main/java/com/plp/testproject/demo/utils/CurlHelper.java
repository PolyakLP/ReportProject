package com.plp.testproject.demo.utils;

import com.google.gson.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class CurlHelper {

    private String command = "";

    private List<JsonObject> DTOToList;

    public List<JsonObject> syncLists(String key, String path) {
        try {
            DTOToList = new ArrayList<>();
            String command = "";
            command = "curl.exe -H \"X-API-Key: " + key + "\" https://my.api.mockaroo.com" + path;
            String pathName = GlobalHelper.CURL_EXE;
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(GlobalHelper.CURL_SPLIT));
            processBuilder.directory(new File(pathName));
            Process process = null;
            process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            System.out.println("Reading JSON files... " + "please wait");
            BufferedReader bR = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = bR.readLine()) != null) {
                JsonParser jsonParser = new JsonParser();
                JsonArray jsonArr = (JsonArray) jsonParser.parse(line);
                for (int i = 0; i < jsonArr.size(); i++) {
                    JsonObject jsonObj = jsonArr.get(i).getAsJsonObject();
                    if (jsonObj.get("location_id") != null) {
                        jsonObj.addProperty("location", jsonArr.get(i).getAsJsonObject().get("location_id").getAsString());
                        jsonObj.remove("location_id");
                    }
                    addToList(jsonObj);
                }
            }
            inputStream.close();
            command = "";
            System.out.println("Reading JSON files...complete");
            return DTOToList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addToList(JsonObject json) {
        DTOToList.add(json);
    }

}
