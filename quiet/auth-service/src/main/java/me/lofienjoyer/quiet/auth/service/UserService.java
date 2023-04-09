package me.lofienjoyer.quiet.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.lofienjoyer.quiet.basemodel.dao.UserInfoDao;
import me.lofienjoyer.quiet.basemodel.dto.CreateUserDto;
import me.lofienjoyer.quiet.basemodel.entity.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

// TODO Abstract to an interface
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserInfoDao userInfoDao;

    private final PasswordEncoder passwordEncoder;

    public UserInfo addUser(CreateUserDto dto) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(dto.getEmail());
        userInfo.setPassword(passwordEncoder.encode(dto.getPassword()));
        try {
            return userInfoDao.save(userInfo);
        } catch (Exception e) {
            log.warn("An error happened trying to save a new user (probably a duplicate email)", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "That email is already being used by another user.");
        }
    }

    public Optional<UserInfo> getByEmail(String email) {
        return userInfoDao.findByEmail(email);
    }

}
