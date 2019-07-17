package com.plp.testproject.demo.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.plp.testproject.demo.entities.MarketPlaces;
import com.plp.testproject.demo.repositories.MarketPlacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarketPlacesService {

    @Autowired
    private MarketPlacesRepository marketPlacesRepository;

    private List<MarketPlaces> marketPlacesList = new ArrayList<>();

    public List<MarketPlaces> getAllListings() {
        return marketPlacesRepository.findAll();
    }

    public MarketPlaces save(MarketPlaces location) {
        return marketPlacesRepository.save(location);
    }

    public MarketPlaces getMarketPlaceById(Long id) {
        return marketPlacesRepository.findByid(id);
    }

    public void addToList(MarketPlaces marketPlace) {
        if (marketPlace.getId() != null) {
            marketPlacesList.add(marketPlace);
        }
    }

    public MarketPlaces getFromJson(String line) {

        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(line);
        MarketPlaces market = new MarketPlaces();

        market.setId(json.getAsJsonObject().get("id").getAsLong());
        market.setMarketplace_name(json.getAsJsonObject().get("marketplace_name").toString().substring(1, json.getAsJsonObject().get("marketplace_name").toString().length() - 1));

        return market;
    }
}
