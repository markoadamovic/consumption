package com.example.consumption.controller;

import com.example.consumption.model.dto.MeterDto;
import com.example.consumption.model.dto.ProfileDto;
import com.example.consumption.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/read-fraction-data")
    public ResponseEntity<List<ProfileDto>> processFractionData(@RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok().body(fileService.processAndSaveProfileFractionData(file));
    }

    @PostMapping("/read-meter-reading-data")
    public ResponseEntity<List<MeterDto>> processMeterReadingData(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(fileService.processAndSaveMeterReadingsData(file));
    }

}
