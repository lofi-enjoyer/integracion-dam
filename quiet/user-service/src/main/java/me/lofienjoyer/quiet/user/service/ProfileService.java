package me.lofienjoyer.quiet.user.service;

import me.lofienjoyer.quiet.basemodel.entity.Profile;
import reactor.core.publisher.Mono;

/**
 * Handles profiles
 */
public interface ProfileService {

    /**
     * @param email Email of the profile to fetch
     * @return Profile with the specified email
     */
    Mono<Profile> getProfileByEmail(String email);

    /**
     * @param username Username of the profile to fetch
     * @return Profile with the specified email
     */
    Mono<Profile> getProfileByUsername(String username);

}
