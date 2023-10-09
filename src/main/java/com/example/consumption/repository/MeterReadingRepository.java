package com.example.consumption.repository;

import com.example.consumption.model.entity.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {

    @Query("SELECT mr FROM MeterReading mr " +
            "JOIN Meter m ON mr.meter.id = :meterId " +
            "WHERE m.profile.id = :profileId " +
            "AND mr.dateOfMeasuring = :timeOfMeasuring ")
    Optional<MeterReading> findMeterReading(@Param("profileId") Long profileId,
                                            @Param("meterId") Long meterId,
                                            @Param("timeOfMeasuring") LocalDateTime timeOfMeasuring);

    @Query("SELECT mr FROM MeterReading mr " +
            "JOIN Meter m ON mr.meter.id = :meterId " +
            "WHERE m.profile.id = :profileId " +
            "AND m.id = :meterId " +
            "AND mr.id = :meterReadingId ")
    Optional<MeterReading> findByProfileAndMeterAndId(@Param("profileId") Long profileId,
                                                      @Param("meterId") Long meterId,
                                                      @Param("meterReadingId") Long meterReadingId);

    @Query("SELECT mr FROM MeterReading  mr " +
            "WHERE mr.meter.id = :meterId " +
            "AND mr.meter.profile.id = :profileId")
    List<MeterReading> findAll(@Param("meterId") Long meterId,
                               @Param("profileId") Long profileId);
}
