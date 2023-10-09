package com.example.consumption.controller;

import com.example.consumption.model.dto.UpdateFractionDto;
import com.example.consumption.model.dto.FractionDto;
import com.example.consumption.service.FractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/profile/{profileId}/fractions")
public class FractionsController {

    private final FractionService fractionService;

    @GetMapping("/{fractionId}")
    public ResponseEntity<FractionDto> getFraction(@PathVariable Long profileId,
                                                   @PathVariable Long fractionId) {
        return ResponseEntity.ok().body(fractionService.getFraction(profileId, fractionId));
    }

    @GetMapping("/list")
    public ResponseEntity<List<FractionDto>> getFractions(@PathVariable Long profileId) {
        return ResponseEntity.ok().body(fractionService.getFractions(profileId));
    }

    @PostMapping
    public ResponseEntity<FractionDto> createFraction(@PathVariable Long profileId,
                                                      @RequestBody FractionDto fractionDto) {
        return ResponseEntity.ok().body(fractionService.createFraction(profileId, fractionDto));
    }

    @PutMapping("/{fractionId}")
    public ResponseEntity<FractionDto> updateFraction(@PathVariable Long profileId,
                                                      @PathVariable Long fractionId,
                                                      @RequestBody FractionDto fractionDto) {
        return ResponseEntity.ok().body(fractionService.updateFraction(profileId, fractionId, fractionDto));
    }

    @PutMapping
    public ResponseEntity<List<FractionDto>> updateFractions(@PathVariable Long profileId,
                                                             @RequestBody UpdateFractionDto fractionDto) {
        return ResponseEntity.ok().body(fractionService.updateFractions(profileId, fractionDto));
    }


    @DeleteMapping("/{fractionId}")
    public ResponseEntity<FractionDto> deleteFraction(@PathVariable Long profileId,
                                                      @PathVariable Long fractionId) {
        fractionService.deleteFraction(profileId, fractionId);
        return ResponseEntity.noContent().build();
    }


}
