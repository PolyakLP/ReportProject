package com.plp.testproject.demo.resources;


import com.plp.testproject.demo.entities.Locations;
import com.plp.testproject.demo.repositories.LocationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class LocationController {
    @Autowired
    private LocationsRepository locationsRepository;

    @GetMapping("/location")
    public ResponseEntity<List<Locations>> getLocations(){
        return new ResponseEntity<>(locationsRepository.findAll(), HttpStatus.OK);
    }
}
