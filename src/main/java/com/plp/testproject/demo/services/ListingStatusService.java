package com.plp.testproject.demo.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.plp.testproject.demo.entities.ListingStatus;
import com.plp.testproject.demo.repositories.ListingStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListingStatusService {

    @Autowired
    private ListingStatusRepository listingStatusRepository;

    private List<ListingStatus> listingStatusList = new ArrayList<>();


    public List<ListingStatus> getAllListings() {
        return listingStatusRepository.findAll();
    }

    public ListingStatus save(ListingStatus location) {
        return listingStatusRepository.save(location);
    }

    public void saveList(List<ListingStatus> location) {
        for (ListingStatus list :
                location) {
            listingStatusRepository.save(list);
        }
    }

    public ListingStatus getListingStatusById(Integer id) {
        return listingStatusRepository.findByid(id);
    }

    public void addToList(ListingStatus location) {
        if (location.getId() != null) {
            listingStatusList.add(location);
        }
    }


    public ListingStatus getFromJson(String line) {
        JsonElement json = new JsonObject();
        JsonParser parser = new JsonParser();
        json = parser.parse(line);
        ListingStatus listStat = new ListingStatus();

        listStat.setId(json.getAsJsonObject().get("id").getAsInt());
        listStat.setStatus_name(json.getAsJsonObject().get("status_name").toString().substring(1,json.getAsJsonObject().get("status_name").toString().length()-1));

        return listStat;
    }
}
