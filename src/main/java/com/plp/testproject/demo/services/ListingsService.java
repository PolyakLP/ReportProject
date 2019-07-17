package com.plp.testproject.demo.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.plp.testproject.demo.entities.ListingStatus;
import com.plp.testproject.demo.entities.Listings;
import com.plp.testproject.demo.entities.Locations;
import com.plp.testproject.demo.entities.MarketPlaces;
import com.plp.testproject.demo.repositories.ListingsRepository;
import com.plp.testproject.demo.repositories.LocationsRepository;
import com.plp.testproject.demo.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ListingsService {

    @Autowired
    private ListingsRepository listingsRepository;
    @Autowired
    private LocationsService locationsService;
    @Autowired
    private ListingStatusService listingStatusService;
    @Autowired
    private MarketPlacesService marketPlacesService;
    @Autowired
    private Validation validation;
    @Autowired
    private LocationsRepository locationsRepository;


    private List<Listings> listingsList = new ArrayList<>();

    private LinkedHashMap<Listings, Integer> listingsNotValidList = new LinkedHashMap<>();

    public List<Listings> getAllListings() {
        return listingsRepository.findAll();
    }

    public Listings save(Listings listing) {
        return listingsRepository.save(listing);
    }

    public Listings getFromJson(String line) throws ParseException {
        Listings listing = new Listings();
        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(line);

        String uid = json.getAsJsonObject().get("id").toString().substring(1, json.getAsJsonObject().get("id").toString().length() - 1);
        String currency = json.getAsJsonObject().get("currency").toString();

        listing.setUuid(uid);
        listing.setTitle(json.getAsJsonObject().get("title").toString().substring(1, json.getAsJsonObject().get("title").toString().length() - 1));
        listing.setDescription(json.getAsJsonObject().get("description").toString().substring(1, json.getAsJsonObject().get("description").toString().length() - 1));
        listing.setListing_price(json.getAsJsonObject().get("listing_price").getAsBigDecimal());
        listing.setCurrency(currency.substring(1, currency.length() - 1));
        listing.setQuantity(json.getAsJsonObject().get("quantity").getAsInt());
        String incomeDate = json.getAsJsonObject().get("upload_time").toString().substring(1, json.getAsJsonObject().get("upload_time").toString().length() - 1);
        if (!incomeDate.equals(null) && !incomeDate.equals("ul")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            LocalDate localDate = LocalDate.parse(incomeDate, formatter);
            listing.setUpload_time(localDate);
        } else {
            listing.setUpload_time(null);
        }
        listing.setOwner_email_address(json.getAsJsonObject().get("owner_email_address").toString().substring(1, json.getAsJsonObject().get("owner_email_address").toString().length() - 1));
        Long listing_status = json.getAsJsonObject().get("listing_status").getAsLong();
        ListingStatus listingStatus = new ListingStatus();
        if (listingStatusService.getListingStatusById(listing_status) != null) {
            if (listingStatusService.getListingStatusById(listing_status).getStatus_name() != null) {
                listingStatus = listingStatusService.getListingStatusById(listing_status);
            }
        }
        Long marketPlace = json.getAsJsonObject().get("marketplace").getAsLong();
        MarketPlaces marketplace = new MarketPlaces();
        if (marketPlacesService.getMarketPlaceById(marketPlace) != null) {
            if (marketPlacesService.getMarketPlaceById(marketPlace).getMarketplace_name() != null) {
                marketplace = marketPlacesService.getMarketPlaceById(marketPlace);
            }
        }
        if (listingStatus.getStatus_name() != null) {
            listing.setListingstatus(listingStatus);
        }
        if (marketplace.getMarketplace_name() != null) {
            listing.setMarketplaces(marketplace);
        }
        String loc = json.getAsJsonObject().get("location").toString().substring(1, json.getAsJsonObject().get("location").toString().length() - 1);
        Locations help = locationsService.findByUuid(loc);
        if (help != null) {
            listing.setLocations(help);
        }

        return listing;
    }

    public void addToList(Listings listing) {
        if (listing != null) {
            listingsList.add(listing);
        }
    }

    public void addToNotValid(LinkedHashMap<Listings, Integer> badValues) {
        for (Map.Entry<Listings, Integer> entry : badValues.entrySet()) {
            Listings key = entry.getKey();
            Integer value = entry.getValue();
            if (!listingsNotValidList.containsValue(key)) {
                listingsNotValidList.put(key, value);
            }
        }
    }

    public LinkedHashMap<Listings, Integer> getListingsNotValidList() {
        return listingsNotValidList;
    }

    public Long countByListing() {
        return listingsRepository.count();
    }

    public Long countByMarketPlace(Long l) {
        MarketPlaces marketPlaces = marketPlacesService.getMarketPlaceById(l);
        return listingsRepository.countByListingWithMarket(marketPlaces.getId());
    }

    public BigDecimal avgMarketListingPrice(long l) {
        MarketPlaces marketPlaces = marketPlacesService.getMarketPlaceById(l);
        return listingsRepository.avgListingPrice(marketPlaces.getId()).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getTotalListingPriceWithMarketPlace(long l) {
        MarketPlaces marketPlaces = marketPlacesService.getMarketPlaceById(l);
        return listingsRepository.sumListingPriceWithMarket(marketPlaces.getId()).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public String getBestListerEmail() {
        if (listingsRepository.bestLister().size() > 1) {
            return listingsRepository.bestLister().get(0);
        }
        return null;
    }

    public String getBestListerEmailMonth(LocalDate beginMonth, LocalDate endMonth) {
        if (listingsRepository.bestListerMonths(beginMonth, endMonth).size() > 1) {
            return listingsRepository.bestListerMonths(beginMonth, endMonth).get(0);
        }
        return null;
    }

    public Long getByMonthsTotalListPriceWithMarket(Long l, LocalDate beginMonth, LocalDate endMonth) {
        MarketPlaces marketPlaces = marketPlacesService.getMarketPlaceById(l);
        return listingsRepository.countByMarketplacesMonths(beginMonth, endMonth, marketPlaces.getId());
    }

    public BigDecimal getMarketTotalListingPrice(Long l, LocalDate beginMonth, LocalDate endMonth) {
        MarketPlaces marketPlaces = marketPlacesService.getMarketPlaceById(l);
        return listingsRepository.sumTotalMarketplacesMonths(beginMonth, endMonth, marketPlaces.getId()).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getAvgMarketListingPrice(Long l, LocalDate beginMonth, LocalDate endMonth) {
        MarketPlaces marketPlaces = marketPlacesService.getMarketPlaceById(l);
        return listingsRepository.avgMarketListingPriceMonths(beginMonth, endMonth, marketPlaces.getId()).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
