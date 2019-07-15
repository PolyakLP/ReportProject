package com.plp.testproject.demo.repositories;

import com.plp.testproject.demo.entities.ListingStatus;
import com.plp.testproject.demo.entities.Listings;
import com.plp.testproject.demo.entities.Locations;
import com.plp.testproject.demo.entities.MarketPlaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ListingsRepository extends CustomRepository<Listings, Long> {
   // List<Listings> findByLocations(Long locId);
    Listings findByid(Long id);


    Long countByMarketplaces(MarketPlaces marketPlaces);


    //Long countByListing_price(MarketPlaces marketPlaces);

//    @Query(value = "SELECT AVG(e.listing_price) FROM Listings e WHERE e.marketplaces = ?1" , nativeQuery = true)
//    Long avarageByListing_price(Long marketId);
}
