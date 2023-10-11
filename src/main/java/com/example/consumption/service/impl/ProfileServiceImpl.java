package com.example.consumption.service.impl;

import com.example.consumption.exception.NotFoundException;
import com.example.consumption.model.dto.ProfileDto;
import com.example.consumption.model.entity.Profile;
import com.example.consumption.repository.ProfileRepository;
import com.example.consumption.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public Profile getProfileModel(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException(String.format("Profile with id %s not found", profileId)));
    }

    @Override
    public List<ProfileDto> saveProfiles(List<Profile> profiles) {
        return profileRepository.saveAll(profiles)
                .stream()
                .map(ProfileDto::fromEntity)
                .collect(Collectors.toList());
    }

}
