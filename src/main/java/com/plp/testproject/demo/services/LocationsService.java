package com.plp.testproject.demo.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.plp.testproject.demo.entities.Locations;
import com.plp.testproject.demo.repositories.LocationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LocationsService {

    @Autowired
    private LocationsRepository locationRepository;

    @Autowired
    private ListingsService listingsService;

    private List<Locations> locationList = new ArrayList<>();


    public List<Locations> getAllLocations() {
        return locationRepository.findAll();
    }



    public Locations save(Locations location) {
        return locationRepository.save(location);
    }

    public void saveList(List<Locations> location) {
        for (Locations list :
                location) {
            locationRepository.save(list);
        }
    }

    public Locations getLocationById(Long id) {
        Locations loc = locationRepository.findByid(id);
        return loc;
    }

    public Locations getFromJson(String line) {
        JsonElement json = new JsonObject();
        JsonParser parser = new JsonParser();
        json = parser.parse(line);
        Locations location = new Locations();

        location.setUuid(json.getAsJsonObject().get("id").toString().substring(1, json.getAsJsonObject().get("id").toString().length() - 1));
        location.setManager_name(json.getAsJsonObject().get("manager_name").toString().substring(1, json.getAsJsonObject().get("manager_name").toString().length() - 1));
        location.setAddress_primary(json.getAsJsonObject().get("address_primary").toString().substring(1, json.getAsJsonObject().get("address_primary").toString().length() - 1));
        location.setAddress_secondary(json.getAsJsonObject().get("address_secondary").toString().substring(1, json.getAsJsonObject().get("address_secondary").toString().length() - 1));
        location.setAddress_secondary(json.getAsJsonObject().get("address_secondary").toString());
        location.setPhone(json.getAsJsonObject().get("phone").toString().toString().substring(1, json.getAsJsonObject().get("phone").toString().length() - 1));
        location.setCountry(json.getAsJsonObject().get("country").toString().substring(1, json.getAsJsonObject().get("country").toString().length() - 1));
        location.setTown(json.getAsJsonObject().get("town").toString().substring(1, json.getAsJsonObject().get("town").toString().length() - 1));
        location.setPostal_code(json.getAsJsonObject().get("postal_code").toString().substring(1, json.getAsJsonObject().get("postal_code").toString().length() - 1));

        if(locationRepository.findByUuid(location.getUuid())!=null){
            location = locationRepository.findByUuid(location.getUuid());
        }
        return location;
    }


    public void addToList(Locations location) {
        if (location.getUuid() != null) {
            locationList.add(location);
        }
    }

    public Locations findByLocation(Long id) {
        return locationRepository.findByid(id);
    }

    public Locations findByUuid(String uuid){
        return locationRepository.findByUuid(uuid);
    }

    public void refresh(Locations help) {
        locationRepository.refresh(help);
    }
}
