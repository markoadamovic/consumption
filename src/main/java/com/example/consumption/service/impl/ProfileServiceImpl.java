package com.example.consumption.service.impl;

import com.example.consumption.exception.NotFoundException;
import com.example.consumption.model.entity.Profile;
import com.example.consumption.repository.ProfileRepository;
import com.example.consumption.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void saveProfiles(List<Profile> profiles) {
        profileRepository.saveAll(profiles);
    }

}
