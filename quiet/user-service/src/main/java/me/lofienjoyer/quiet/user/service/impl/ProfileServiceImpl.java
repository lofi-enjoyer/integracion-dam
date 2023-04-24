package me.lofienjoyer.quiet.user.service.impl;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.basemodel.dao.ProfileDao;
import me.lofienjoyer.quiet.basemodel.dao.UserInfoDao;
import me.lofienjoyer.quiet.basemodel.dto.ProfileDto;
import me.lofienjoyer.quiet.basemodel.entity.Profile;
import me.lofienjoyer.quiet.basemodel.entity.UserInfo;
import me.lofienjoyer.quiet.user.service.ProfileService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

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
        Optional<UserInfo> userInfoOptional = userInfoDao.findByEmail(email);

        if (userInfoOptional.isEmpty())
            return Mono.error(new UsernameNotFoundException("Email not found."));

        UserInfo userInfo = userInfoOptional.get();

        Optional<Profile> profileOptional = profileDao.findByUser(userInfo);

        if (profileOptional.isEmpty())
            return Mono.error(new UsernameNotFoundException("Profile not found"));

        return Mono.just(profileOptional.get())
                .map(ProfileDto::new);
    }

    @Override
    public Mono<ProfileDto> getProfileByUsername(String username) {
        Optional<Profile> profileOptional = profileDao.findByUsername(username);

        if (profileOptional.isEmpty())
            return Mono.error(new UsernameNotFoundException("Username not found."));

        return Mono.just(profileOptional.get())
                .map(ProfileDto::new);
    }

    @Override
    public Flux<ProfileDto> getRecommendations() {
        return null;
    }

}
