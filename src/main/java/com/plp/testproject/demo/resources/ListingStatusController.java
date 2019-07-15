package com.plp.testproject.demo.resources;

import com.plp.testproject.demo.entities.ListingStatus;
import com.plp.testproject.demo.entities.Listings;
import com.plp.testproject.demo.repositories.ListingStatusRepository;
import com.plp.testproject.demo.repositories.ListingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ListingStatusController {

    @Autowired
    private ListingStatusRepository listingStatusRepository;

    @GetMapping("/listingStatus")
    public ResponseEntity<List<ListingStatus>> getListingStatus(){
        return new ResponseEntity<>(listingStatusRepository.findAll(), HttpStatus.OK);
    }
}
