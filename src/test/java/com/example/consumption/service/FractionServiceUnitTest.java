package com.example.consumption.service;

import com.example.consumption.exception.BadRequestException;
import com.example.consumption.exception.NotFoundException;
import com.example.consumption.model.dto.FractionDto;
import com.example.consumption.model.entity.Fraction;
import com.example.consumption.model.entity.Meter;
import com.example.consumption.model.entity.Profile;
import com.example.consumption.repository.FractionRepository;
import com.example.consumption.service.impl.FractionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.example.consumption.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FractionServiceUnitTest extends BaseUnitTest {

    @Mock
    FractionRepository fractionRepository;

    @Mock
    ProfileService profileService;

    @InjectMocks
    FractionServiceImpl fractionService;

    Profile profile;

    Meter meter;

    Fraction fraction;

    @BeforeEach
    public void setUp() {
        profile = createProfile();
        meter = profile.getMeter();
        fraction = profile.getFractions().get(0);
    }

    @Test
    void shouldGetFractionModel() {
        when(fractionRepository.findByIdAndProfileId(profile.getId(), fraction.getId())).thenReturn(Optional.of(fraction));

        Fraction expected = fractionService.getFractionModel(profile.getId(), fraction.getId());
        assertEquals(expected.getValue(), fraction.getValue());
        assertEquals(expected.getMonth(), fraction.getMonth());
        assertEquals(expected.getProfile().getId(), fraction.getProfile().getId());
    }

    @Test
    void shouldThrowNotFoundException_whenGetFractionModel_ifFractionIsNotFound() {
        when(fractionRepository.findByIdAndProfileId(profile.getId(), fraction.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class,
                () -> fractionService.getFractionModel(profile.getId(), fraction.getId()));

        assertTrue(exception.getMessage().contains(NOT_FOUND));
    }

    @Test
    void shouldGetFraction() {
        when(fractionRepository.findByIdAndProfileId(profile.getId(), fraction.getId())).thenReturn(Optional.of(fraction));

        FractionDto expected = fractionService.getFraction(profile.getId(), fraction.getId());
        assertEquals(expected.getValue(), fraction.getValue());
        assertEquals(expected.getMonth(), fraction.getMonth());
        assertEquals(expected.getProfileId(), fraction.getProfile().getId());
    }

    @Test
    void shouldGetFractions() {
        when(profileService.getProfileModel(profile.getId())).thenReturn(profile);

        List<FractionDto> expected = fractionService.getFractions(profile.getId());
        assertEquals(expected.size(), profile.getFractions().size());
        assertEquals(expected.get(0).getValue(), profile.getFractions().get(0).getValue());
        assertEquals(expected.get(0).getMonth(), profile.getFractions().get(0).getMonth());
        assertEquals(expected.get(0).getProfileId(), profile.getFractions().get(0).getProfile().getId());
    }

    @Test
    void shouldCreateFraction() {
        FractionDto fractionDto = FractionDto.builder()
                .month(JUNE)
                .value(0.05)
                .build();
        Fraction createdFraction = FractionDto.toEntity(fractionDto);
        createdFraction.setProfile(profile);
        when(profileService.getProfileModel(profile.getId())).thenReturn(profile);
        when(fractionRepository.save(any())).thenReturn(createdFraction);

        FractionDto expected = fractionService.createFraction(profile.getId(), fractionDto);

        assertEquals(expected.getMonth(), fractionDto.getMonth());
        assertEquals(expected.getValue(), fractionDto.getValue());
        assertEquals(expected.getProfileId(), fractionDto.getProfileId());
    }

    @Test
    void shouldThrowBadRequestException_whenCreateFraction_ifMonthAlreadyExists() {
        FractionDto fractionDto = FractionDto.builder()
                .month(JANUARY)
                .value(0.05)
                .build();
        when(profileService.getProfileModel(profile.getId())).thenReturn(profile);

        Exception exception = assertThrows(BadRequestException.class,
                () -> fractionService.createFraction(profile.getId(), fractionDto));
        assertTrue(exception.getMessage().contains(BAD_REQUEST));
    }

    @Test
    void shouldThrowBadRequestException_whenCreateFraction_ifValueIsGreaterThen1() {
        FractionDto fractionDto = FractionDto.builder()
                .month(JUNE)
                .value(0.2)
                .build();
        when(profileService.getProfileModel(profile.getId())).thenReturn(profile);

        Exception exception = assertThrows(BadRequestException.class,
                () -> fractionService.createFraction(profile.getId(), fractionDto));
        assertTrue(exception.getMessage().contains(BAD_REQUEST));
    }
}
