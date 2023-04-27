package me.lofienjoyer.quiet.user.service;

import me.lofienjoyer.quiet.basemodel.dto.ProfileDto;
import me.lofienjoyer.quiet.basemodel.entity.Profile;
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

    Flux<ProfileDto> getRecommendations();

}
