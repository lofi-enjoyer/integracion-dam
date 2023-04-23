package me.lofienjoyer.quiet.user.service.impl;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.basemodel.dao.ProfileDao;
import me.lofienjoyer.quiet.basemodel.dao.UserInfoDao;
import me.lofienjoyer.quiet.basemodel.dto.CreateUserDto;
import me.lofienjoyer.quiet.basemodel.dto.RegisterResponseDto;
import me.lofienjoyer.quiet.basemodel.entity.Profile;
import me.lofienjoyer.quiet.basemodel.entity.UserInfo;
import me.lofienjoyer.quiet.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Implementation of {@link UserService}
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${quiet.defaultdesc}")
    private String defaultDescription;

    private final UserInfoDao userInfoDao;
    private final ProfileDao profileDao;
    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<UserInfo> getUserById(long id) {
        Optional<UserInfo> userInfoOptional = userInfoDao.findById(id);

        if (userInfoOptional.isEmpty())
            return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND));

        return Mono.just(userInfoOptional.get());
    }

    @Override
    public Mono<UserInfo> getUserByEmail(String email) {
        Optional<UserInfo> userInfoOptional = userInfoDao.findByEmail(email);

        if (userInfoOptional.isEmpty())
            return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND));

        return Mono.just(userInfoOptional.get());
    }

    @Override
    public Mono<RegisterResponseDto> registerNewUser(CreateUserDto dto) {
        return webClientBuilder.build().post().uri("http://auth-service/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(UserInfo.class)
                .map(userInfo -> {
                    Profile profile = new Profile();
                    profile.setUsername(dto.getUsername());
                    profile.setName(dto.getUsername());
                    profile.setDescription(String.format(defaultDescription, "@" + dto.getUsername()));
                    profileDao.save(profile);

                    userInfo.setProfile(profile);
                    return userInfoDao.save(userInfo);
                })
                .map(RegisterResponseDto::new)
                .onErrorReturn(new RegisterResponseDto());
    }

}
