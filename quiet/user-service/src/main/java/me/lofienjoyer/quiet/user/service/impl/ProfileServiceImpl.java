package me.lofienjoyer.quiet.user.service.impl;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.basemodel.dao.ProfileDao;
import me.lofienjoyer.quiet.basemodel.dao.UserInfoDao;
import me.lofienjoyer.quiet.basemodel.dto.EditProfileDto;
import me.lofienjoyer.quiet.basemodel.dto.FollowRequestDto;
import me.lofienjoyer.quiet.basemodel.dto.ProfileDto;
import me.lofienjoyer.quiet.basemodel.entity.Profile;
import me.lofienjoyer.quiet.basemodel.entity.UserInfo;
import me.lofienjoyer.quiet.user.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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
    public Flux<ProfileDto> getRecommendations(Authentication authentication) {
        UserInfo user = userInfoDao.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        Profile userProfile = profileDao.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User profile not found"));

        List<Long> followedProfilesIds = profileDao.getFollowingProfilesIds(userProfile.getId());

        Random random = new Random();
        List<Profile> allProfiles = profileDao.findAll();
        allProfiles.removeIf(profile -> followedProfilesIds.contains(profile.getId()));
        allProfiles.removeIf(profile -> profile.getId() == userProfile.getId());
        List<Profile> recommendedProfiles = new ArrayList<>();
        int allProfilesSize = allProfiles.size();
        for (int i = 0; i < Math.min(3, allProfilesSize); i++) {
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

    @Override
    public Mono<Integer> followProfile(Authentication authentication, String username) {
        UserInfo user = userInfoDao.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        Profile userProfile = profileDao.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User profile not found"));

        Profile profileToFollow = profileDao.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile to follow not found"));

        if (profileDao.isFollowing(userProfile.getId(), profileToFollow.getId()) != 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Already following that user");
        }

        profileDao.addFollow(userProfile.getId(), profileToFollow.getId());
        return Mono.just(profileDao.getFollowerCount(profileToFollow.getId()));
    }

    @Override
    public Mono<Integer> unfollowProfile(Authentication authentication, String username) {
        UserInfo user = userInfoDao.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        Profile userProfile = profileDao.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User profile not found"));

        Profile profileToFollow = profileDao.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile to follow not found"));

        profileDao.removeFollow(userProfile.getId(), profileToFollow.getId());
        return Mono.just(profileDao.getFollowerCount(profileToFollow.getId()));
    }

    @Override
    public Mono<ProfileDto> editProfile(EditProfileDto dto, Authentication authentication) {
        UserInfo userInfo = userInfoDao.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        Profile profile = profileDao.findByUser(userInfo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User profile not found."));

        Optional<Profile> possibleConflictingProfile = profileDao.findByUsername(dto.getUsername());
        if (possibleConflictingProfile.isPresent() && !possibleConflictingProfile.get().getUsername().equals(profile.getUsername()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "That username is already being used!");

        profile.setUsername(dto.getUsername().trim().toLowerCase());
        profile.setName(dto.getName().trim());
        profile.setDescription(dto.getDescription().trim());
        return Mono.just(new ProfileDto(profileDao.save(profile), 0));
    }

    @Override
    public Mono<Boolean> isFollowing(FollowRequestDto dto, Authentication authentication) {
        UserInfo userInfo = userInfoDao.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        Profile profile = profileDao.findByUser(userInfo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User profile not found."));

        Profile profileToFollow = profileDao.findByUsername(dto.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile to follow not found"));

        if (profile.getUsername().equals(dto.getUsername()))
            throw new ResponseStatusException(HttpStatus.CONFLICT);

        return Mono.just(profileDao.isFollowing(profile.getId(), profileToFollow.getId()) != 0);
    }

}
