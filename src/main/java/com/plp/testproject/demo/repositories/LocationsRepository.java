package com.plp.testproject.demo.repositories;

import com.plp.testproject.demo.entities.Locations;
import org.springframework.stereotype.Repository;


@Repository
public interface LocationsRepository extends CustomRepository<Locations, Long> {
    Locations findByid(Long uuid);

    Locations findByUuid(String uuid);

}
