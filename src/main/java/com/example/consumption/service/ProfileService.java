package com.example.consumption.service;

import com.example.consumption.model.dto.ProfileDto;
import com.example.consumption.model.entity.Profile;

import java.util.List;

public interface ProfileService {

    Profile getProfileModel(Long profileId);

    List<ProfileDto> saveProfiles(List<Profile> profiles);

}
