package com.plp.testproject.demo.repositories;

import com.plp.testproject.demo.entities.ListingStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingStatusRepository extends CustomRepository<ListingStatus, Long> {
    ListingStatus findByid(Long id);
}
