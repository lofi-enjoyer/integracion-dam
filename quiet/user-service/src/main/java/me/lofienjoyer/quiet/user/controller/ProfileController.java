package me.lofienjoyer.quiet.user.controller;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.basemodel.dto.EditProfileDto;
import me.lofienjoyer.quiet.basemodel.dto.FollowRequestDto;
import me.lofienjoyer.quiet.basemodel.dto.ProfileDto;
import me.lofienjoyer.quiet.user.service.ProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    /**
     * @param authentication Current authenticated user
     * @return Profile with the specified email
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public Mono<ProfileDto> getCurrentProfile(Authentication authentication) {
        return profileService.getProfileByEmail(authentication.getName());
    }

    //TODO Use requests params instead of path variables
    /**
     * @param username Username of the profile to fetch
     * @return Profile with the specified username
     */
    @GetMapping("/{username}")
    public Mono<ProfileDto> getProfileByUsername(@PathVariable("username") String username) {
        return profileService.getProfileByUsername(username);
    }

    /**
     * @param authentication User's authentication
     * @return Recommended profiles for a user
     */
    @GetMapping("/recommendations")
    @PreAuthorize("isAuthenticated()")
    public Flux<ProfileDto> getRecommendations(Authentication authentication) {
        return profileService.getRecommendations(authentication);
    }

    /**
     * @param dto DTO with the necessary data
     * @param authentication User's authentication
     * @return Profile follower count
     */
    @PostMapping("/follow")
    @PreAuthorize("isAuthenticated()")
    public Mono<Integer> followProfile(@RequestBody FollowRequestDto dto, Authentication authentication) {
        return profileService.followProfile(authentication, dto.getUsername());
    }

    /**
     * @param dto DTO with the necessary data
     * @param authentication User's authentication
     * @return Profile follower count
     */
    @PostMapping("/unfollow")
    @PreAuthorize("isAuthenticated()")
    public Mono<Integer> unfollowProfile(@RequestBody FollowRequestDto dto, Authentication authentication) {
        return profileService.unfollowProfile(authentication, dto.getUsername());
    }

    /**
     * @param dto DTO with the necessary data
     * @param authentication User's authentication
     * @return Profile DTO with the updated information
     */
    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public Mono<ProfileDto> editProfile(@RequestBody EditProfileDto dto, Authentication authentication) {
        return profileService.editProfile(dto, authentication);
    }

    /**
     * @param dto DTO with the necessary data
     * @param authentication User's authentication
     * @return True if following, false otherwise
     */
    @PostMapping("/isfollowing")
    public Mono<Boolean> isFollowing(@RequestBody FollowRequestDto dto, Authentication authentication) {
        return profileService.isFollowing(dto, authentication);
    }

}
