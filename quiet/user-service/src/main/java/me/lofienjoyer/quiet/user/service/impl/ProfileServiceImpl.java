package me.lofienjoyer.quiet.user.service.impl;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.basemodel.dao.ProfileDao;
import me.lofienjoyer.quiet.basemodel.dao.UserInfoDao;
import me.lofienjoyer.quiet.basemodel.dto.ProfileDto;
import me.lofienjoyer.quiet.basemodel.entity.Profile;
import me.lofienjoyer.quiet.basemodel.entity.UserInfo;
import me.lofienjoyer.quiet.user.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Implementation of {@link ProfileService}
 */
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserInfoDao userInfoDao;
    private final ProfileDao profileDao;

    @Override
    public Mono<ProfileDto> getProfileByEmail(String email) {
        UserInfo userInfo = userInfoDao.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found."));

        Profile profile = profileDao.findByUser(userInfo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        return Mono.just(profile)
                .map(pr -> {
                    int followerCount = profileDao.getFollowerCount(pr.getId());
                    return new ProfileDto(pr, followerCount);
                });
    }

    @Override
    public Mono<ProfileDto> getProfileByUsername(String username) {
        Profile profile = profileDao.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not found."));

        return Mono.just(profile)
                .map(pr -> {
                    int followerCount = profileDao.getFollowerCount(pr.getId());
                    return new ProfileDto(pr, followerCount);
                });
    }

    @Override
    public Flux<ProfileDto> getRecommendations() {
        Random random = new Random();
        List<Profile> allProfiles = profileDao.findAll();
        List<Profile> recommendedProfiles = new ArrayList<>();
        for (int i = 0; i < Math.min(3, allProfiles.size()); i++) {
            recommendedProfiles.add(
                    allProfiles.remove(random.nextInt(allProfiles.size()))
            );
        }
        return Mono.just(recommendedProfiles)
                .flatMapMany(Flux::fromIterable)
                .map(profile -> {
                    int followerCount = profileDao.getFollowerCount(profile.getId());
                    return new ProfileDto(profile, followerCount);
                });
    }

}
