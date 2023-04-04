package me.lofienjoyer.quiet.auth.dao;

import me.lofienjoyer.quiet.auth.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoDao extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByEmail(String username);

}
