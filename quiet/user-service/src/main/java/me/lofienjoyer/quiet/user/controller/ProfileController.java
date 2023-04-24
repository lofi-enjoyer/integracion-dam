package me.lofienjoyer.quiet.user.controller;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.basemodel.dto.ProfileDto;
import me.lofienjoyer.quiet.basemodel.entity.Profile;
import me.lofienjoyer.quiet.user.service.ProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
     * @return Profile with the specified email
     */
    @GetMapping("/{username}")
    public Mono<ProfileDto> getProfileByUsername(@PathVariable("username") String username) {
        return profileService.getProfileByUsername(username);
    }

    @GetMapping("/recommendations")
    @PreAuthorize("isAuthenticated()")
    public Flux<ProfileDto> getRecommendations() {
        return profileService.getRecommendations();
    }

}
