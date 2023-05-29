package me.lofienjoyer.quiet.user.service;

import me.lofienjoyer.quiet.basemodel.dto.EditProfileDto;
import me.lofienjoyer.quiet.basemodel.dto.ProfileDto;
import me.lofienjoyer.quiet.basemodel.entity.Profile;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Handles profiles
 */
public interface ProfileService {

    /**
     * @param email Email of the profile to fetch
     * @return Profile with the specified email
     */
    Mono<ProfileDto> getProfileByEmail(String email);

    /**
     * @param username Username of the profile to fetch
     * @return Profile with the specified username
     */
    Mono<ProfileDto> getProfileByUsername(String username);

    Flux<ProfileDto> getRecommendations(Authentication authentication);

    Mono<Integer> followProfile(Authentication authentication, String username);

    Mono<Integer> unfollowProfile(Authentication authentication, String username);

    Mono<ProfileDto> editProfile(EditProfileDto dto, Authentication authentication);

}
