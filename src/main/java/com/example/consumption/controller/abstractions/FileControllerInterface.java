package com.example.consumption.controller.abstractions;

import com.example.consumption.model.dto.MeterDto;
import com.example.consumption.model.dto.MeterReadingDto;
import com.example.consumption.model.dto.ProfileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileControllerInterface {

    @Operation(
            description = "Process CSV file and save profile data",
            operationId = "processProfileData",
            summary = "Process profile data",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully processed",
                            content = {@Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProfileDto.class))
                            )}),
                    @ApiResponse(responseCode = "400", description = "Bad request, not valid csv file",
                            content = @Content(mediaType = "application/json"))
            }

    )
    ResponseEntity<List<ProfileDto>> processProfileData(MultipartFile file);

    @Operation(
            description = "Process CSV file and save meter readings data",
            operationId = "processMeterReadingData",
            summary = "Process meter reading data",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully processed",
                            content = {@Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = MeterDto.class))
                            )}),
                    @ApiResponse(responseCode = "400", description = "Bad request, not valid csv file",
                            content = @Content(mediaType = "application/json"))
            }

    )
    ResponseEntity<List<MeterDto>> processMeterReadingData(MultipartFile file);
}
