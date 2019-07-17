package com.plp.testproject.demo;

import com.google.gson.JsonObject;
import com.plp.testproject.demo.entities.ListingStatus;
import com.plp.testproject.demo.entities.Listings;
import com.plp.testproject.demo.entities.Locations;
import com.plp.testproject.demo.entities.MarketPlaces;
import com.plp.testproject.demo.services.*;
import com.plp.testproject.demo.utils.*;
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
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(repositoryBaseClass = CustomService.class)
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner getCurlData(CurlHelper curlHelper) {
        return args -> {
            try {
                CurlHelper curl = new CurlHelper();
                WriterHelper writer = new WriterHelper();
                List<JsonObject> loc = curl.syncLists(GlobalHelper.X_API_KEY, GlobalHelper.CURL_LOCATION_PATH);
                writer.writeToFile(loc, GlobalHelper.CURL_LOCATION_FILENAME);
                System.out.println("Location curl command was succesfull...");
                System.out.println("Input JSON has written to src/main/resources/json/locations.json ...");

                List<JsonObject> listStat = curl.syncLists(GlobalHelper.X_API_KEY, GlobalHelper.CURL_LISTINGSTATUS_PATH);
                writer.writeToFile(listStat, GlobalHelper.CURL_LISTINGSTATUS_FILENAME);
                System.out.println("ListingStatus curl command was succesfull...");
                System.out.println("Input JSON has written to src/main/resources/json/listingstatus.json ...");

                List<JsonObject> market = curl.syncLists(GlobalHelper.X_API_KEY, GlobalHelper.CURL_MARKETPLACE_PATH);
                writer.writeToFile(market, GlobalHelper.CURL_MARKETPLACE_FILENAME);
                System.out.println("MarketPlaces curl command was succesfull...");
                System.out.println("Input JSON has written to src/main/resources/json/marketplaces.json ...");

                List<JsonObject> list = curl.syncLists(GlobalHelper.X_API_KEY, GlobalHelper.CURL_LISTING_PATH);
                writer.writeToFile(list, GlobalHelper.CURL_LISTING_FILENAME);
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
                try (BufferedReader br = new BufferedReader((new FileReader(new File(GlobalHelper.JSON_FILES, GlobalHelper.CURL_LOCATION_FILENAME))))) {
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
                try (BufferedReader br = new BufferedReader((new FileReader(new File(GlobalHelper.JSON_FILES, GlobalHelper.CURL_LISTINGSTATUS_FILENAME))))) {
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
                try (BufferedReader br = new BufferedReader((new FileReader(new File(GlobalHelper.JSON_FILES, GlobalHelper.CURL_MARKETPLACE_FILENAME))))) {
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
                try (BufferedReader br = new BufferedReader((new FileReader(new File(GlobalHelper.JSON_FILES, GlobalHelper.CURL_LISTING_FILENAME))))) {
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
    CommandLineRunner runnerCreateRiport(ListingsService listingsService, LocationsService locationsService, ListingStatusService listingStatusService, MarketPlacesService marketPlacesService, FtpClient ftpClient) {
        return args -> {
            WriterHelper writer = new WriterHelper(listingsService, locationsService, listingStatusService, marketPlacesService, ftpClient);
            writer.createReportData();
            System.out.println("Program run is over...");
            System.out.println("Created by PLP");
        };

    }


}
