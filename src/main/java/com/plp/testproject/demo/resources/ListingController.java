package com.plp.testproject.demo.resources;

import com.plp.testproject.demo.entities.Listings;
import com.plp.testproject.demo.services.ListingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ListingController {

    @Autowired
    private ListingsService listingsService;

    @GetMapping("/listing")
    public ResponseEntity<List<Listings>> getAllListings() {
        return new ResponseEntity<>(listingsService.getAllListings(), HttpStatus.OK);
    }
}
