package com.plp.testproject.demo.resources;

import com.plp.testproject.demo.entities.MarketPlaces;
import com.plp.testproject.demo.repositories.MarketPlacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MarketPlaceController {

    @Autowired
    private MarketPlacesRepository marketPlacesRepository;

    @GetMapping("/marketplace")
    public ResponseEntity<List<MarketPlaces>> getLocations(){
        return new ResponseEntity<>(marketPlacesRepository.findAll(), HttpStatus.OK);
    }
}
