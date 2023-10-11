package com.example.consumption.controller.abstractions;

import com.example.consumption.model.dto.FractionDto;
import com.example.consumption.model.dto.MeterReadingDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "MeterReadingController", description = "The MeterReading API. Contains all the operations that can be performed on MeterReading.")
public interface MeterReadingControllerInterface {

    @Operation(
            description = "Get fractions for provided profile",
            operationId = "getMeterReadings",
            summary = "Get meter readings",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                            content = {@Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = MeterReadingDto.class))
                            )}),
                    @ApiResponse(responseCode = "404", description = "The Profile is not found",
                            content = @Content(mediaType = "application/json"))
            }

    )
    ResponseEntity<List<MeterReadingDto>> getMeterReadings(Long profileId, Long meterId);

    @Operation(
            description = "Get meter reading for provided profile, meter and meterReadingId",
            operationId = "getMeterReading",
            summary = "Get meter reading",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned",
                            content = @Content(schema = @Schema(implementation = MeterReadingDto.class))),
                    @ApiResponse(responseCode = "404", description = "The MeterReading is not found",
                            content = @Content(mediaType = "application/json"))
            }

    )
    ResponseEntity<MeterReadingDto> getMeterReading(Long profileId, Long meterId, Long meterReadingId);

    @Operation(
            description = "Create meter reading for provided profile and meter",
            operationId = "createMeterReading",
            summary = "Create meter reading",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully created",
                            content = @Content(schema = @Schema(implementation = FractionDto.class))),
                    @ApiResponse(responseCode = "404", description = "The Meter is not found",
                            content = @Content(mediaType = "application/json"))
            }

    )
    ResponseEntity<MeterReadingDto> createMeterReading(Long profileId, Long meterId);

    @Operation(
            description = "Delete meter reading for provided profile, meter and meterReadingId",
            operationId = "deleteMeterReading",
            summary = "Delete meter reading",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content",
                            content = @Content(schema = @Schema())),
                    @ApiResponse(responseCode = "404", description = "The MeterReading is not found",
                            content = @Content(mediaType = "application/json"))
            }

    )
    ResponseEntity<Void> deleteMeterReading(Long profileId, Long meterId, Long meterReadingId);
}
