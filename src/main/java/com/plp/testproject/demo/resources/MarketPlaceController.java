package com.plp.testproject.demo.resources;

import com.plp.testproject.demo.entities.MarketPlaces;
import com.plp.testproject.demo.services.MarketPlacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MarketPlaceController {

    @Autowired
    private MarketPlacesService marketPlacesService;

    @GetMapping("/marketplace")
    public ResponseEntity<List<MarketPlaces>> getAllMarketplaces(){
        return new ResponseEntity<>(marketPlacesService.getAllListings(), HttpStatus.OK);
    }
}
