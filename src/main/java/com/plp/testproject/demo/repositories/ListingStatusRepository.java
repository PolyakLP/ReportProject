package com.plp.testproject.demo.repositories;

import com.plp.testproject.demo.entities.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingStatusRepository extends CustomRepository<ListingStatus, Integer> {
   ListingStatus findByid(Integer id);
}
