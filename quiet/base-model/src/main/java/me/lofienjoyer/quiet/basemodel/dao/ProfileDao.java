package me.lofienjoyer.quiet.basemodel.dao;

import me.lofienjoyer.quiet.basemodel.entity.Profile;
import me.lofienjoyer.quiet.basemodel.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repository for {@link me.lofienjoyer.quiet.basemodel.entity.Profile} entities
 */
public interface ProfileDao extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUser(UserInfo userInfo);

    Optional<Profile> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query(value = "SELECT COUNT(*) FROM Profile p WHERE p IN (SELECT pr.following FROM Profile pr WHERE pr.id = :id)")
    int getFollowerCount(@Param("id") long id);

}
