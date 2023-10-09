package com.example.consumption.service;

import com.example.consumption.model.entity.Profile;

import java.util.List;

public interface ProfileService {

    Profile getProfileModel(Long profileId);

    void saveProfiles(List<Profile> profiles);

}
