package com.plp.testproject.demo;

import com.google.gson.JsonObject;
import com.plp.testproject.demo.entities.ListingStatus;
import com.plp.testproject.demo.entities.Listings;
import com.plp.testproject.demo.entities.Locations;
import com.plp.testproject.demo.entities.MarketPlaces;
import com.plp.testproject.demo.repositories.LocationsRepository;
import com.plp.testproject.demo.services.*;
import com.plp.testproject.demo.utils.CurlHelper;
import com.plp.testproject.demo.utils.Validation;
import com.plp.testproject.demo.utils.WriterHelper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(repositoryBaseClass = CustomService.class)
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    private static final String X_API_KEY = "63304c70";

    @Bean
    CommandLineRunner getCurlData(CurlHelper curlHelper) {
        return args -> {
            try {
                CurlHelper curl = new CurlHelper();
                WriterHelper writer = new WriterHelper();
                List<JsonObject> loc = new ArrayList<>();
                loc = curl.syncLocationList(X_API_KEY, "/location");
                writer.writeToFile(loc, "locations.json");
                System.out.println("Location curl command was succesfull...");
                System.out.println("Input JSON has written to src/main/resources/json/locations.json ...");

                List<JsonObject> listStat = curl.syncListingStatusList(X_API_KEY, "/listingStatus");
                writer.writeToFile(listStat, "listingstatus.json");
                System.out.println("ListingStatus curl command was succesfull...");
                System.out.println("Input JSON has written to src/main/resources/json/listingstatus.json ...");

                List<JsonObject> market = curl.syncListingStatusList(X_API_KEY, "/marketplace");
                writer.writeToFile(market, "marketplaces.json");
                System.out.println("MarketPlaces curl command was succesfull...");
                System.out.println("Input JSON has written to src/main/resources/json/marketplaces.json ...");

                List<JsonObject> list = curl.syncListingList(X_API_KEY, "/listing");
                writer.writeToFile(list, "listings.json");
                System.out.println("Listing curl command was succesfull...");
                System.out.println("Input JSON has written to src/main/resources/json/listings.json ...");

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    @Bean
    @DependsOn("getCurlData")
    CommandLineRunner runnerLocation(LocationsService locationsService) {
        return args -> {
            try {
                //read json and write to db
                //List<Locations> inputList = new ArrayList<>();
                try (BufferedReader br = new BufferedReader((new FileReader(new File("src/main/resources/json/", "locations.json"))))) {
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        Locations location = locationsService.getFromJson(line);
                        locationsService.save(location);
                        locationsService.addToList(location);
                    }
                }
                System.out.println("Location Saved");

            } catch (IOException e) {
                System.out.println("Unable to save Location: " + e.getMessage());
            }
        };
    }

    @Bean
    @DependsOn("runnerLocation")
    CommandLineRunner runnerListingStatus(ListingStatusService listingStatusService) {
        return args -> {
            try {
                //read json and write to db
                //List<Listings> inputList = new ArrayList<>();
                try (BufferedReader br = new BufferedReader((new FileReader(new File("src/main/resources/json/", "listingstatus.json"))))) {
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        ListingStatus listingStat = listingStatusService.getFromJson(line);
                        ListingStatus saved = listingStatusService.save(listingStat);
                        listingStatusService.addToList(saved);
                    }
                }
                System.out.println("ListingStatus Saved...");
            } catch (IOException e) {
                System.out.println("Unable to save ListingStatus: " + e.getMessage());
            }
        };
    }

    @Bean
    @DependsOn("runnerListingStatus")
    CommandLineRunner runnerMarketPlaces(MarketPlacesService marketPlacesService) {
        return args -> {
            try {
                //read json and write to db
                try (BufferedReader br = new BufferedReader((new FileReader(new File("src/main/resources/json/", "marketplaces.json"))))) {
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        MarketPlaces market = marketPlacesService.getFromJson(line);
                        MarketPlaces saved = marketPlacesService.save(market);
                        marketPlacesService.addToList(saved);
                    }
                }
                System.out.println("MarketPlaces Saved...");
            } catch (IOException e) {
                System.out.println("Unable to save MarketPlaces: " + e.getMessage());
            }
        };
    }

    @Bean
    @DependsOn("runnerMarketPlaces")
    CommandLineRunner runnerListing(ListingsService listingsService, LocationsService locationsService, ListingStatusService listingStatusService, MarketPlacesService marketPlacesService) {
        return args -> {
            try {
                //read json and write to db
                Validation validation = new Validation(listingsService, listingStatusService, marketPlacesService, locationsService);
                try (BufferedReader br = new BufferedReader((new FileReader(new File("src/main/resources/json/", "listings.json"))))) {
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        Listings listing = listingsService.getFromJson(line);
                        if (listing != null) {
                            if (validation.validate(listing)) {
                                Listings saved = listingsService.save(listing);
                                listingsService.addToList(saved);
                            } else {
                                listingsService.addToNotValid(validation.getBadValues());
                            }

                        }
                    }
                }
                validation.writeList(listingsService.getListingsNotValidList());
                System.out.println("Listings Saved...");
            } catch (IOException e) {
                System.out.println("Unable to save Listings: " + e.getMessage());
            }
        };
    }

    @Bean
    @DependsOn("runnerListing")
    CommandLineRunner runnerCreateRiport(ListingsService listingsService, LocationsService locationsService, ListingStatusService listingStatusService, MarketPlacesService marketPlacesService) {
        return args -> {
            WriterHelper writer = new WriterHelper(listingsService,  locationsService,  listingStatusService,  marketPlacesService);
            writer.createReportData( );
        };

    }


}
