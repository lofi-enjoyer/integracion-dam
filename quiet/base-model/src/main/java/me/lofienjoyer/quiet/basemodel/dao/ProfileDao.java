package me.lofienjoyer.quiet.basemodel.dao;

import jakarta.transaction.Transactional;
import me.lofienjoyer.quiet.basemodel.entity.Profile;
import me.lofienjoyer.quiet.basemodel.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query(value = "SELECT COUNT(*) FROM Profile p WHERE p IN (SELECT pr.followers FROM Profile pr WHERE pr.id = :id)")
    int getFollowerCount(@Param("id") long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO follows (profile_id, followed_id) VALUES (:profileId, :followedId)", nativeQuery = true)
    void addFollow(@Param("profileId") long profileId, @Param("followedId") long followedId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM follows WHERE profile_id = :profileId AND followed_id = :followedId", nativeQuery = true)
    void removeFollow(@Param("profileId") long profileId, @Param("followedId") long followedId);

    @Query(value = "SELECT COUNT(*) FROM follows WHERE profile_id = :profileId AND followed_id = :followedId", nativeQuery = true)
    int isFollowing(@Param("profileId") long profileId, @Param("followedId") long followedId);

}
