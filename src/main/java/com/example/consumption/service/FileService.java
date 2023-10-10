package com.example.consumption.service;

import com.example.consumption.model.dto.MeterDto;
import com.example.consumption.model.dto.ProfileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    List<ProfileDto> processAndSaveProfileFractionData(MultipartFile file);

    List<MeterDto> processAndSaveMeterReadingsData(MultipartFile file);

}
