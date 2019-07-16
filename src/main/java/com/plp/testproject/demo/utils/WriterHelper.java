package com.plp.testproject.demo.utils;

import com.google.gson.JsonArray;
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
import java.time.LocalDate;
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
    @Autowired
    private FtpClient ftpClient;

    public WriterHelper() {
    }

    public WriterHelper(ListingsService listingsService, LocationsService locationsService, ListingStatusService listingStatusService, MarketPlacesService marketPlacesService, FtpClient ftpClient) {
        this.listingsService = listingsService;
        this.listingStatusService = listingStatusService;
        this.locationsService = locationsService;
        this.marketPlacesService = marketPlacesService;
        this.ftpClient = ftpClient;
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

    public void writeToReportJson(JsonArray array, String filename) throws IOException {
        File reportJson = new File("src/main/resources/json/report", filename);
        FileWriter writer = new FileWriter(reportJson);
        writer.write(array.toString());
        writer.close();
        FtpClient client = new FtpClient();
        client.open();
        client.putFileToPath(reportJson,"src/main/resources/json/report");
        client.close();
        System.out.println("report.json updated to FTP server");
    }

    public JsonArray createReportData() throws IOException {

        LocalDate beginMonth = LocalDate.parse("2018-04-01");
        LocalDate endMonth = LocalDate.parse("2018-04-30");
        Long EBAY = 1L;
        Long AMAZON = 2L;

        //Report
        Long totalListingCount = this.listingsService.countByListing();
        Long totalEbayListing = this.listingsService.countByMarketPlace(EBAY);
        BigDecimal totalEbayListingPrice = this.listingsService.getTotalListingPriceWithMarketPlace(EBAY);
        BigDecimal avarageEbayListingPrice = this.listingsService.avgMarketListingPrice(EBAY);
        Long totalAmazonListing = this.listingsService.countByMarketPlace(AMAZON);
        BigDecimal totalAmazonListingPrice = this.listingsService.getTotalListingPriceWithMarketPlace(AMAZON);
        BigDecimal avarageAmazonListingPrice = this.listingsService.avgMarketListingPrice(AMAZON);
        String bestListerEmail = this.listingsService.getBestListerEmail();

        //Monthly reports get
        Long totalEbayListingCountPerMonth = this.listingsService.getByMonthsTotalListPriceWithMarket(EBAY, beginMonth, endMonth);
        BigDecimal totalEbayListingPricePerMonth = this.listingsService.getMarketTotalListingPrice(EBAY, beginMonth, endMonth);
        BigDecimal avgEbayListingPriceMonths = this.listingsService.getAvgMarketListingPrice(EBAY, beginMonth, endMonth);
        BigDecimal avgAmazonListingPriceMonths = this.listingsService.getAvgMarketListingPrice(AMAZON, beginMonth, endMonth);
        BigDecimal totalAmazonListingPricePerMonth = this.listingsService.getMarketTotalListingPrice(AMAZON, beginMonth, endMonth);
        Long totalAmazonListingCountPerMonth = this.listingsService.getByMonthsTotalListPriceWithMarket(AMAZON, beginMonth, endMonth);
        String bestListerEmailMonths = this.listingsService.getBestListerEmailMonth(beginMonth, endMonth);

        JsonObject monthlyReport = new JsonObject();
        monthlyReport.addProperty("TotalEbayCount", totalEbayListingCountPerMonth);
        monthlyReport.addProperty("TotalEbayPrice", totalEbayListingPricePerMonth);
        monthlyReport.addProperty("AvgEbayPrice", avgEbayListingPriceMonths);
        monthlyReport.addProperty("AvgAmazonPrice", avgAmazonListingPriceMonths);
        monthlyReport.addProperty("TotalAmazonPrice", totalAmazonListingPricePerMonth);
        monthlyReport.addProperty("TotalAmazonCount", totalAmazonListingCountPerMonth);
        monthlyReport.addProperty("BestListerEmail", bestListerEmailMonths);

        JsonObject report = new JsonObject();
        report.addProperty("TotalCount", totalListingCount);
        report.addProperty("TotalEbayCount", totalEbayListing);
        report.addProperty("TotalEbayPrice", totalEbayListingPrice);
        report.addProperty("AvgEbayPrice", avarageEbayListingPrice);
        report.addProperty("TotalAmazonCount", totalAmazonListing);
        report.addProperty("TotalAmazonPrice", totalAmazonListingPrice);
        report.addProperty("AvgAmazonPrice", avarageAmazonListingPrice);
        report.addProperty("BestListerEmail", bestListerEmail);
        report.add("Monthly_04", monthlyReport);

        JsonArray array = new JsonArray();
        array.add(report);
        writeToReportJson(array, "report.json");

        return array;
    }


}
