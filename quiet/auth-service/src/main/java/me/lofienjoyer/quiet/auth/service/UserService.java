package me.lofienjoyer.quiet.auth.service;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.auth.dao.UserInfoDao;
import me.lofienjoyer.quiet.auth.model.UserInfo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserInfoDao userInfoDao;

    private final PasswordEncoder passwordEncoder;

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoDao.save(userInfo);
        return "user added to system ";
    }

    public Optional<UserInfo> getByEmail(String email) {
        return userInfoDao.findByEmail(email);
    }

}
