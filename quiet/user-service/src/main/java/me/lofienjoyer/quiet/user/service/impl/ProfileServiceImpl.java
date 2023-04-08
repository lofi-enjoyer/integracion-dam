package me.lofienjoyer.quiet.user.service.impl;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.basemodel.dao.ProfileDao;
import me.lofienjoyer.quiet.basemodel.dao.UserInfoDao;
import me.lofienjoyer.quiet.basemodel.entity.Profile;
import me.lofienjoyer.quiet.basemodel.entity.UserInfo;
import me.lofienjoyer.quiet.user.service.ProfileService;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserInfoDao userInfoDao;
    private final ProfileDao profileDao;
    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<Profile> getProfileByEmail(String email) {
        Optional<UserInfo> userInfoOptional = userInfoDao.findByEmail(email);

        if (userInfoOptional.isEmpty())
            return Mono.error(new UsernameNotFoundException("Email not found."));

        UserInfo userInfo = userInfoOptional.get();

        Optional<Profile> profileOptional = profileDao.findByUser(userInfo);

        if (profileOptional.isEmpty())
            return Mono.error(new UsernameNotFoundException("Profile not found"));

        return Mono.just(profileOptional.get());
    }

    @Override
    public Mono<Profile> getProfileByUsername(String username) {
        Optional<Profile> profileOptional = profileDao.findByUsername(username);

        if (profileOptional.isEmpty())
            return Mono.error(new UsernameNotFoundException("Username not found."));

        return Mono.just(profileOptional.get());
    }
}
