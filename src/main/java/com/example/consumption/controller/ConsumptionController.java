package com.example.consumption.controller;

import com.example.consumption.controller.abstractions.ConsumptionControllerInterface;
import com.example.consumption.model.dto.ConsumptionDto;
import com.example.consumption.service.ConsumptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/profile/{profileId}/meter/{meterId}/consumption")
public class ConsumptionController implements ConsumptionControllerInterface {

    private final ConsumptionService consumptionService;

    @GetMapping
    public ResponseEntity<ConsumptionDto> getConsumptionByMonth(@PathVariable Long profileId,
                                                                @PathVariable Long meterId,
                                                                @RequestParam("month") String month) {
        return ResponseEntity.ok()
                .body(consumptionService.getConsumptionByMonth(profileId, meterId, month));
    }

}
