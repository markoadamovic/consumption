package com.example.consumption.controller;

import com.example.consumption.controller.abstractions.MeterReadingControllerInterface;
import com.example.consumption.model.dto.MeterReadingDto;
import com.example.consumption.service.MeterReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/profile/{profileId}/meter/{meterId}/meter-reading")
public class MeterReadingController implements MeterReadingControllerInterface {

    private final MeterReadingService meterReadingService;

    @GetMapping
    public ResponseEntity<List<MeterReadingDto>> getMeterReadings(@PathVariable Long profileId,
                                                                  @PathVariable Long meterId) {
        return ResponseEntity.ok().body(meterReadingService.getMeterReadings(profileId, meterId));
    }

    @GetMapping("/{meterReadingId}")
    public ResponseEntity<MeterReadingDto> getMeterReading(@PathVariable Long profileId,
                                                           @PathVariable Long meterId,
                                                           @PathVariable Long meterReadingId) {
        return ResponseEntity.ok().body(meterReadingService.getMeterReading(profileId, meterId, meterReadingId));
    }

    @PostMapping
    public ResponseEntity<MeterReadingDto> createMeterReading(@PathVariable Long profileId,
                                                              @PathVariable Long meterId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(meterReadingService.createMeterReading(profileId, meterId));
    }

    @DeleteMapping("/{meterReadingId}")
    public ResponseEntity<Void> deleteMeterReading(@PathVariable Long profileId,
                                                   @PathVariable Long meterId,
                                                   @PathVariable Long meterReadingId) {
        meterReadingService.deleteMeterReading(profileId, meterId, meterReadingId);
        return ResponseEntity.noContent().build();
    }

}


