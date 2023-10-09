package com.example.consumption.repository;

import com.example.consumption.model.entity.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MeterRepository extends JpaRepository<Meter, Long> {

    @Query("SELECT m FROM Meter m " +
            "WHERE m.profile.name = :profileName " +
            "AND m.id = :meterId")
    Optional<Meter> findByProfileNameAndId(@Param("meterId") Long meterId,
                                           @Param("profileName") String profileName);

    @Query("SELECT m FROM Meter m " +
            "WHERE m.profile.id = :profileId " +
            "AND m.id = :meterId")
    Optional<Meter> findByProfileAndId(@Param("profileId") Long profileId,
                                       @Param("meterId") Long meterId);

}
