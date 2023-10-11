package com.example.consumption.controller.abstractions;

import com.example.consumption.model.dto.FractionDto;
import com.example.consumption.model.dto.UpdateFractionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "FractionController", description = "The Fraction API. Contains all the operations that can be performed on fraction.")
public interface FractionControllerInterface {

    @Operation(
            description = "Get fraction for provided profile and fractionId",
            operationId = "getFraction",
            summary = "Get fraction",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned",
                            content = @Content(schema = @Schema(implementation = FractionDto.class))),
                    @ApiResponse(responseCode = "404", description = "The Fraction is not found",
                            content = @Content(mediaType = "application/json"))
            }

    )
    ResponseEntity<FractionDto> getFraction(Long profileId, Long fractionId);

    @Operation(
            description = "Get fractions for provided profile",
            operationId = "getFractions",
            summary = "Get fractions",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                            content = {@Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = FractionDto.class))
                            )}),
                    @ApiResponse(responseCode = "404", description = "The Profile is not found",
                            content = @Content(mediaType = "application/json"))
            }

    )
    ResponseEntity<List<FractionDto>> getFractions(Long profileId);

    @Operation(
            description = "Create fraction for provided profile",
            operationId = "createFraction",
            summary = "Create fraction",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully created",
                            content = @Content(schema = @Schema(implementation = FractionDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request, fraction for provided month is already created",
                            content = @Content(schema = @Schema(implementation = FractionDto.class))),
                    @ApiResponse(responseCode = "404", description = "The Profile is not found",
                            content = @Content(mediaType = "application/json"))
            }

    )
    ResponseEntity<FractionDto> createFraction(Long profileId, FractionDto fractionDto);

    @Operation(
            description = "Update fraction",
            operationId = "updateFraction",
            summary = "Update fraction",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated",
                            content = @Content(schema = @Schema(implementation = FractionDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request, fraction value is not equal to 1",
                            content = @Content(schema = @Schema(implementation = FractionDto.class))),
                    @ApiResponse(responseCode = "404", description = "The Fraction is not found",
                            content = @Content(mediaType = "application/json"))
            }

    )
    ResponseEntity<FractionDto> updateFraction(Long profileId, Long fractionId, FractionDto fractionDto);

    @Operation(
            description = "Update fractions for provided profile",
            operationId = "updateFractions",
            summary = "Get updateFractions",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated",
                            content = {@Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = FractionDto.class))
                            )}),
                    @ApiResponse(responseCode = "400", description = "Fractions must have exactly 12 entries with a total value of 1.0",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "The Profile is not found",
                            content = @Content(mediaType = "application/json"))
            }

    )
    ResponseEntity<List<FractionDto>> updateFractions(Long profileId, UpdateFractionDto fractionDto);

    @Operation(
            description = "Delete fraction for provided profile and fractionId",
            operationId = "deleteFraction",
            summary = "Delete fraction",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content",
                            content = @Content(schema = @Schema())),
                    @ApiResponse(responseCode = "404", description = "The Fraction is not found",
                            content = @Content(mediaType = "application/json"))
            }

    )
    ResponseEntity<Void> deleteFraction(Long profileId, Long fractionId);
}
