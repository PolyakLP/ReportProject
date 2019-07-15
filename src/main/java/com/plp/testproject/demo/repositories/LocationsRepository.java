package com.plp.testproject.demo.repositories;

import com.plp.testproject.demo.entities.Locations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocationsRepository extends CustomRepository<Locations, Long> {
   // List<Locations> findByUuidList(String uuid);
//   @Query(value = "SELECT l FROM Locations l WHERE l.uuid = :uuid" )
//   Locations findByLocation(@Param("uuid") String uuid);

    Locations findByid(Long uuid);

    Locations findByUuid(String uuid);

}
