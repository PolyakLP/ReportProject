package com.plp.testproject.demo.utils;

import com.plp.testproject.demo.entities.ListingStatus;
import com.plp.testproject.demo.entities.Listings;
import com.plp.testproject.demo.entities.Locations;
import com.plp.testproject.demo.entities.MarketPlaces;
import com.plp.testproject.demo.repositories.ListingStatusRepository;
import com.plp.testproject.demo.repositories.LocationsRepository;
import com.plp.testproject.demo.repositories.MarketPlacesRepository;
import com.plp.testproject.demo.services.ListingStatusService;
import com.plp.testproject.demo.services.ListingsService;
import com.plp.testproject.demo.services.LocationsService;
import com.plp.testproject.demo.services.MarketPlacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Service
public class Validation {

    List<Listings> good = new ArrayList<>();
    List<Listings> bad = new ArrayList<>();
    List<Listings> outList = new ArrayList<>();
    LinkedHashMap<Listings, Integer> badValues = new LinkedHashMap<>();

    @Autowired
    private LocationsService locationsService;
    @Autowired
    private LocationsRepository locationsRepository;
    @Autowired
    private ListingStatusService listingStatusService;
    @Autowired
    private MarketPlacesService marketPlacesService;
    @Autowired
    private ListingsService listingsService;


    public Validation(ListingsService listingsService, ListingStatusService listingStatusService, MarketPlacesService marketPlacesService, LocationsService locationsService) {
        this.marketPlacesService = marketPlacesService;
        this.locationsService = locationsService;
        this.listingStatusService = listingStatusService;
        this.listingsService = listingsService;
    }


    public boolean validate(Listings list) {

        if (list.getUuid() == null) {
            //bad.add(list);
            badValues.put(list, 1);
            return false;
        }
        if (list.getCurrency() == null && list.getCurrency().length() != 3) {
            badValues.put(list, 2);
            return false;
        }
        if (list.getTitle() == null) {
            badValues.put(list, 3);
            return false;
        }
        if (list.getDescription() == null) {
            badValues.put(list, 4);
            return false;
        }
        if (list.getQuantity() == null && list.getQuantity() < 0) {
            badValues.put(list, 5);
            return false;
        }
        if (list.getListing_price() == null) {
            badValues.put(list, 6);
            return false;
        } else {
            if (list.getListing_price().intValue() < 0 && list.getListing_price().setScale(2).intValue() != 2) {
                badValues.put(list, 6);
                return false;
            }
        }
        if (list.getListingstatus() == null) {
            badValues.put(list, 7);
            return false;
        } else {
            Long id = list.getListingstatus().getId();
            if (id == null) {
                badValues.put(list, 7);
                return false;
            } else {
                ListingStatus status = new ListingStatus();
                status = listingStatusService.getListingStatusById(id);
                if (status == null) {
                    badValues.put(list, 7);
                    return false;
                }
            }
        }
        if (list.getMarketplaces() == null) {
            badValues.put(list, 8);
            return false;
        } else {
            Long id = list.getMarketplaces().getId();
            if (id == null) {
                badValues.put(list, 8);
                return false;
            } else {
                MarketPlaces market = new MarketPlaces();
                market = marketPlacesService.getMarketPlaceById(id);
                if (market == null) {
                    badValues.put(list, 8);
                    return false;
                }
            }
        }
        if (!Validation.isValidEmailAddress(list.getOwner_email_address())) {
            badValues.put(list, 9);
            return false;
        }
        if (list.getLocations() == null) {
            badValues.put(list, 10);
            return false;
        } else {
            String id = list.getLocations().getUuid();
            if (id == null) {
                badValues.put(list, 10);
                return false;
            } else {
                Locations locations = locationsService.findByUuid(id);
                if (locations == null) {
                    badValues.put(list, 10);
                    return false;
                }
            }
        }

        return true;
    }


    public void writeList(LinkedHashMap<Listings, Integer> listingsNotValidList) {
        try {
            System.out.println("Start writing to CSV...");

            FileWriter writer = new FileWriter(new File("src/main/resources/csv/", "importLog.csv"));
            for (Map.Entry<Listings, Integer> entry : listingsNotValidList.entrySet()) {
                Listings key = entry.getKey();
                Integer value = entry.getValue();
                String invalidColumn = "";
                switch (value) {
                    case 1:
                        invalidColumn = "Id";
                        break;
                    case 2:
                        invalidColumn = "Currency";
                        break;
                    case 3:
                        invalidColumn = "Title";
                        break;
                    case 4:
                        invalidColumn = "Description";
                        break;
                    case 5:
                        invalidColumn = "Quantity";
                        break;
                    case 6:
                        invalidColumn = "ListingPrice";
                        break;
                    case 7:
                        invalidColumn = "ListingStatus";
                        break;
                    case 8:
                        invalidColumn = "MarketPlace";
                        break;
                    case 9:
                        invalidColumn = "Email";
                        break;
                    case 10:
                        invalidColumn = "Location";
                        break;
                    default:
                        break;
                }
                StringBuilder bld = new StringBuilder();
                bld.append(key.getUuid() + ";");
                if (key.getMarketplaces() != null) {
                    bld.append(key.getMarketplaces().getMarketplace_name() + ";");
                } else {
                    bld.append("UNKNOWN;");
                }
                bld.append(invalidColumn);
                String line = bld.toString();
                writer.write(line + System.lineSeparator());
            }
            writer.close();
            System.out.println("End writing to CSV...");
            good.clear();
            bad.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public LinkedHashMap<Listings, Integer> getBadValues() {
        return badValues;
    }

    public void setBadValues(LinkedHashMap<Listings, Integer> badValues) {
        this.badValues = badValues;
    }
}
