package me.lofienjoyer.quiet.user.service;

import me.lofienjoyer.quiet.basemodel.dto.EditProfileDto;
import me.lofienjoyer.quiet.basemodel.dto.FollowRequestDto;
import me.lofienjoyer.quiet.basemodel.dto.ProfileDto;
import me.lofienjoyer.quiet.basemodel.entity.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * @param authentication User's authentication
     * @return Recommended profiles for a user
     */
    Flux<ProfileDto> getRecommendations(Authentication authentication);

    /**
     * @param authentication User's authentication
     * @param username Profile username
     * @return
     */
    Mono<Integer> followProfile(Authentication authentication, String username);

    /**
     * @param authentication User's authentication
     * @param username Profile username
     * @return
     */
    Mono<Integer> unfollowProfile(Authentication authentication, String username);

    /**
     * @param dto DTO with the necessary data
     * @param authentication User's authentication
     * @return
     */
    Mono<ProfileDto> editProfile(EditProfileDto dto, Authentication authentication);

    /**
     * @param dto DTO with the necessary data
     * @param authentication User's authentication
     * @return True if following, false otherwise
     */
    Mono<Boolean> isFollowing(@RequestBody FollowRequestDto dto, Authentication authentication);

}
