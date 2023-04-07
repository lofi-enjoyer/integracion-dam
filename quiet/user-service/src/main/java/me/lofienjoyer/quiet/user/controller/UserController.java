package me.lofienjoyer.quiet.user.controller;

import me.lofienjoyer.quiet.basemodel.dao.UserInfoDao;
import me.lofienjoyer.quiet.basemodel.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/internal")
public class UserController {

    @Autowired
    UserInfoDao userInfoDao;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public Flux<UserInfo> getAllUsers() {
        return Flux.fromStream(userInfoDao.findAll().stream());
    }

}
