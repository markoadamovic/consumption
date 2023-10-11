package com.example.consumption.service;

import com.example.consumption.exception.NotFoundException;
import com.example.consumption.model.dto.ProfileDto;
import com.example.consumption.model.entity.Meter;
import com.example.consumption.model.entity.Profile;
import com.example.consumption.repository.ProfileRepository;
import com.example.consumption.service.impl.ProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.example.consumption.utils.TestUtils.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProfileServiceUnitTest extends BaseUnitTest {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileServiceImpl profileService;

    Profile profile;

    Meter meter;

    @BeforeEach
    public void setUp() {
        profile = createProfile();
        meter = profile.getMeter();
    }

    @Test
    void shouldGetProfileModel() {
        when(profileRepository.findById(profile.getId())).thenReturn(Optional.of(profile));

        Profile expected = profileService.getProfileModel(profile.getId());

        assertEquals(expected.getId(), profile.getId());
        assertEquals(expected.getName(), profile.getName());
        assertEquals(expected.getMeter().getId(), profile.getMeter().getId());
        assertEquals(expected.getFractions().size(), profile.getFractions().size());
    }

    @Test
    void shouldThrowNotFoundException_whenGetProfileModel_ifProfileIsNotFound() {
        when(profileRepository.findById(profile.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class,
                () -> profileService.getProfileModel(profile.getId()));

        assertTrue(exception.getMessage().contains(NOT_FOUND));
    }

    @Test
    void shouldSaveProfiles() {
        when(profileRepository.saveAll(List.of(profile))).thenReturn(List.of(profile));

        List<ProfileDto> expected = profileService.saveProfiles(List.of(profile));

        assertEquals(expected.get(0).getId(), profile.getId());
        assertEquals(expected.get(0).getName(), profile.getName());
        assertEquals(expected.get(0).getMeter().getId(), profile.getMeter().getId());
        assertEquals(expected.get(0).getFractions().size(), profile.getFractions().size());
        assertEquals(expected.get(0).getFractions().get(0).getProfileId(), profile.getFractions().get(0).getProfile().getId());
        assertEquals(expected.get(0).getFractions().get(0).getValue(), profile.getFractions().get(0).getValue());
    }

}
