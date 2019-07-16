package com.plp.testproject.demo.resources;

import com.plp.testproject.demo.entities.Locations;
import com.plp.testproject.demo.services.LocationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class LocationController {

    @Autowired
    private LocationsService locationsService;

    @GetMapping("/location")
    public ResponseEntity<List<Locations>> getAllLocations(){
        return new ResponseEntity<>(locationsService.getAllLocations(), HttpStatus.OK);
    }
}
