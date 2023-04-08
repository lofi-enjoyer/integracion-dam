package me.lofienjoyer.quiet.basemodel.dao;

import me.lofienjoyer.quiet.basemodel.entity.Profile;
import me.lofienjoyer.quiet.basemodel.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileDao extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUser(UserInfo userInfo);

    Optional<Profile> findByUsername(String username);

}
