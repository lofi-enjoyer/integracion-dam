package me.lofienjoyer.quiet.basemodel.dao;

import me.lofienjoyer.quiet.basemodel.entity.Post;
import me.lofienjoyer.quiet.basemodel.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostDao extends JpaRepository<Post, Long> {

    List<Post> findByProfileOrderByDateDesc(Profile profile);

    @Query(value = "SELECT p FROM Post p LEFT JOIN FETCH p.profile LEFT JOIN FETCH p.likes WHERE p.profile IN (SELECT pr.following FROM Profile pr WHERE pr.id = :id)")
    List<Post> getPostsFromFollowed(@Param("id") long profileId);

}
