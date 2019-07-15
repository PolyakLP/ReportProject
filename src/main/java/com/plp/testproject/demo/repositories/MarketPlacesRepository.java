package com.plp.testproject.demo.repositories;

import com.plp.testproject.demo.entities.MarketPlaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketPlacesRepository extends CustomRepository<MarketPlaces,Long> {
  MarketPlaces findByid(Long id);
}
