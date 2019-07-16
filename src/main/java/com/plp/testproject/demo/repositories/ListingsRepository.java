package com.plp.testproject.demo.repositories;

import com.plp.testproject.demo.entities.Listings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Repository
public interface ListingsRepository extends CustomRepository<Listings, Long> {
    Listings findByid(Long id);

    @Query(value = "SELECT  COUNT(l) FROM Listings l WHERE l.marketplace_id= :marketId GROUP BY l.marketplace_id;", nativeQuery = true)
    Long countByMarketplaces(Long marketId);

    @Query(value = "SELECT  MAX(l.listing_price) FROM Listings l WHERE l.marketplace_id= :marketId GROUP BY l.marketplace_id;", nativeQuery = true)
    BigDecimal countListingPrice(@Param("marketId")Long marketId);

    @Query(value = "SELECT  AVG(l.listing_price) FROM Listings l WHERE l.marketplace_id= :marketId GROUP BY l.marketplace_id;", nativeQuery = true)
    Long avgListingPrice(@Param("marketId")Long marketId);

    @Query(value = "select count(listing_price), owner_email_address\n" +
            "            FROM public.listings\n" +
            "            group by owner_email_address\n" +
            "            order by count DESC;",nativeQuery = true)
    List<String> bestLister();

    //SELECT  *
    //  FROM public.listings
    //  where upload_time between '2018-06-01' And  '2019-01-01';
    

}
