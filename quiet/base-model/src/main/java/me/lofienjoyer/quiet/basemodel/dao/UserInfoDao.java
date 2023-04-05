package me.lofienjoyer.quiet.basemodel.dao;

import me.lofienjoyer.quiet.basemodel.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoDao extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByEmail(String username);

}
