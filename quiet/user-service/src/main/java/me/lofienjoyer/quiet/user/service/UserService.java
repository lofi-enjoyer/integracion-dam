package me.lofienjoyer.quiet.user.service;

import me.lofienjoyer.quiet.basemodel.dto.CreateUserDto;
import me.lofienjoyer.quiet.basemodel.dto.RegisterResponseDto;
import me.lofienjoyer.quiet.basemodel.entity.UserInfo;
import reactor.core.publisher.Mono;

/**
 * Handles users
 */
public interface UserService {

    /**
     * @param id ID of the user to retrieve
     * @return User with the specified id
     */
    Mono<UserInfo> getUserById(long id);

    /**
     * @param email Email of the user to retrieve
     * @return User with the specified email
     */
    Mono<UserInfo> getUserByEmail(String email);

    /**
     * @param dto DTO containing the data for the new user
     * @return User info of the user created
     */
    Mono<RegisterResponseDto> registerNewUser(CreateUserDto dto);

}
