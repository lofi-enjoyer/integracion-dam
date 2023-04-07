package me.lofienjoyer.quiet.user.service;

import me.lofienjoyer.quiet.basemodel.dto.CreateUserDto;
import me.lofienjoyer.quiet.basemodel.entity.UserInfo;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserInfo> getUserById(long id);

    Mono<UserInfo> getUserByEmail(String email);

    Mono<UserInfo> registerNewUser(CreateUserDto dto);

}
