package com.example.consumption.controller.abstractions;

import com.example.consumption.model.dto.ConsumptionDto;
import com.example.consumption.model.dto.FractionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "ConsumptionController", description = "The Consumption API. Contains all the operations that can be performed on consumption.")
public interface ConsumptionControllerInterface {

    @Operation(
            description = "Get consumption for provided profile and meter by month",
            operationId = "getConsumptionByMonth",
            summary = "Get consumption by month",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned",
                            content = @Content(schema = @Schema(implementation = FractionDto.class))),
                    @ApiResponse(responseCode = "404", description = "The MeterReading for provided month is not found",
                            content = @Content(mediaType = "application/json"))
            }

    )
    ResponseEntity<ConsumptionDto> getConsumptionByMonth(Long profileId, Long meterId, String month);

}
