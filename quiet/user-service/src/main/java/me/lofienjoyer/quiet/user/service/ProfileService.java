package me.lofienjoyer.quiet.user.service;

import me.lofienjoyer.quiet.basemodel.entity.Profile;
import reactor.core.publisher.Mono;

public interface ProfileService {

    Mono<Profile> getProfileByEmail(String email);

    Mono<Profile> getProfileByUsername(String username);

}
