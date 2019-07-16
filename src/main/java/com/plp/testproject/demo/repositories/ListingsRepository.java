package com.plp.testproject.demo.repositories;

import com.plp.testproject.demo.entities.Listings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Repository
public interface ListingsRepository extends CustomRepository<Listings, Long> {
    Listings findByid(Long id);

    @Query(value = "SELECT  COUNT(l) FROM Listings l WHERE l.marketplace_id= :marketId GROUP BY l.marketplace_id;", nativeQuery = true)
    Long countByListingWithMarket(@Param("marketId") Long marketId);

    @Query(value = "SELECT  SUM(l.listing_price) FROM Listings l WHERE l.marketplace_id= :marketId GROUP BY l.marketplace_id;", nativeQuery = true)
    BigDecimal sumListingPriceWithMarket(@Param("marketId") Long marketId);

    @Query(value = "SELECT  AVG(l.listing_price) FROM Listings l WHERE l.marketplace_id= :marketId GROUP BY l.marketplace_id;", nativeQuery = true)
    BigDecimal avgListingPrice(@Param("marketId") Long marketId);

    @Query(value = "SELECT l.owner_email_address FROM Listings l GROUP BY l.owner_email_address ORDER BY COUNT(l.owner_email_address) DESC;", nativeQuery = true)
    List<String> bestLister();

    @Query(value = "SELECT  count(l) FROM Listings l WHERE l.upload_time BETWEEN :begin AND :end AND l.marketplace_id = :marketId ;", nativeQuery = true)
    Long countByMarketplacesMonths(@Param("begin") LocalDate begin, @Param("end") LocalDate end, @Param("marketId") Long marketId);

    @Query(value = "SELECT  sum(l.listing_price) FROM Listings l WHERE l.upload_time BETWEEN :begin AND  :end AND l.marketplace_id = :marketId ;", nativeQuery = true)
    BigDecimal sumTotalMarketplacesMonths(@Param("begin") LocalDate begin, @Param("end") LocalDate end, @Param("marketId") Long marketId);

    @Query(value = "SELECT  AVG(l.listing_price) FROM Listings l WHERE l.upload_time BETWEEN :begin AND :end AND l.marketplace_id = :marketId ;", nativeQuery = true)
    BigDecimal avgMarketListingPriceMonths(@Param("begin") LocalDate begin, @Param("end") LocalDate end, @Param("marketId") Long marketId);

    @Query(value = "SELECT l.owner_email_address FROM Listings l WHERE l.upload_time BETWEEN :begin AND :end GROUP BY l.owner_email_address ORDER BY COUNT(l.owner_email_address) DESC;", nativeQuery = true)
    List<String> bestListerMonths(@Param("begin") LocalDate begin, @Param("end") LocalDate end);

}
