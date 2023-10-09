package com.example.consumption.configuration;

import com.example.consumption.model.entity.Meter;
import com.example.consumption.model.entity.Profile;
import com.example.consumption.model.enums.EntityStatus;
import com.example.consumption.service.MeterService;
import com.example.consumption.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final MeterService meterService;

    private final ProfileService profileService;

    @Scheduled(cron = "0 0 0 1 1 ?")
    public void executeYearlyTask() {
        List<Meter> meters = meterService.getAllMeters();
        List<Profile> profiles = meters.stream()
                .map(meter -> {
                    Profile profile = meter.getProfile();
                    Meter newMeter = Meter.builder()
                            .profile(profile)
                            .build();
                    meter.setStatus(EntityStatus.DELETED);
                    profile.setMeter(newMeter);
                    return profile;
                }).collect(Collectors.toList());
        profileService.saveProfiles(profiles);
    }
}
