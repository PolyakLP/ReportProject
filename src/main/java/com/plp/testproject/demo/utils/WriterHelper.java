package com.plp.testproject.demo.utils;

import com.google.gson.JsonObject;
import com.plp.testproject.demo.entities.ListingStatus;
import com.plp.testproject.demo.entities.Listings;
import com.plp.testproject.demo.entities.Locations;
import com.plp.testproject.demo.entities.MarketPlaces;
import com.plp.testproject.demo.services.ListingStatusService;
import com.plp.testproject.demo.services.ListingsService;
import com.plp.testproject.demo.services.LocationsService;
import com.plp.testproject.demo.services.MarketPlacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class WriterHelper {

    @Autowired
    private LocationsService locationsService;
    @Autowired
    private ListingStatusService listingStatusService;
    @Autowired
    private MarketPlacesService marketPlacesService;
    @Autowired
    private ListingsService listingsService;

    public WriterHelper() {
    }

    public WriterHelper(ListingsService listingsService, LocationsService locationsService, ListingStatusService listingStatusService, MarketPlacesService marketPlacesService) {
        this.listingsService = listingsService;
        this.listingStatusService = listingStatusService;
        this.locationsService = locationsService;
        this.marketPlacesService = marketPlacesService;
    }

    public void writeToFile(List<JsonObject> lists, String fileName) throws IOException {
        System.out.println("Start reading..." + fileName);
        FileWriter writer = new FileWriter(new File("src/main/resources/json/", fileName));
        for (JsonObject list : lists) {
            writer.write(list + System.lineSeparator());
        }
        writer.close();
        System.out.println("Finish reading..." + fileName);
    }

    public void createReportData(/*ListingsService listingsService, LocationsService locationsService, ListingStatusService listingStatusService, MarketPlacesService marketPlacesService*/) {

//        List<ListingStatus> listingStatus = this.listingStatusService.getAllLocations();
//        List<MarketPlaces> marketPlace = this.marketPlacesService.getAllLocations();
//        List<Locations> location = this.locationsService.getAllLocations();
//        List<Listings> listing = this.listingsService.getAllLocations();
        // EBAY:1
        //AMAZON:2
        //ACTIVE: 1
        //SCHEDULED: 2
        //CANCELED: 3
        Long totalListingCount = this.listingsService.countByListing();
        Long totalEbayListing = this.listingsService.countByMarketPlace(1L);
        //Long totalEbayListingPrice = this.listingsService.getTotalWithMarketPlace(1L);
        //Long avarageEbayListingPrice = this.listingsService.avgEbayListingPrice(1L);
        Long totalAmazonListing = this.listingsService.countByMarketPlace(2L);
        

        System.out.println("Fákjú");
    }
}
