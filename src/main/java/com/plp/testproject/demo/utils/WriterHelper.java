package com.plp.testproject.demo.utils;

import com.google.gson.JsonObject;
import com.plp.testproject.demo.services.ListingStatusService;
import com.plp.testproject.demo.services.ListingsService;
import com.plp.testproject.demo.services.LocationsService;
import com.plp.testproject.demo.services.MarketPlacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
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

        Long totalListingCount = this.listingsService.countByListing();

        Long totalEbayListing = this.listingsService.countByMarketPlace(1L);
        BigDecimal totalEbayListingPrice = this.listingsService.getMaxListingPriceWithMarketPlace(1L);
        Long avarageEbayListingPrice = this.listingsService.avgMarketListingPrice(1L);

        Long totalAmazonListing = this.listingsService.countByMarketPlace(2L);
        BigDecimal totalAmazonListingPrice = this.listingsService.getMaxListingPriceWithMarketPlace(2L);
        Long avarageAmazonListingPrice = this.listingsService.avgMarketListingPrice(2L);

        String bestListerEmail = this.listingsService.getBestListerEmail();

      //  BigDecimal totalEbayListingPricePerMonth = this.listingsService.getByMonths();

    }
}
