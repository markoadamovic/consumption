package com.example.consumption.service;

import com.example.consumption.model.dto.MeterDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    void processAndSaveProfileFractionData(MultipartFile file);

    List<MeterDto> processAndSaveMeterReadingsData(MultipartFile file);

}
