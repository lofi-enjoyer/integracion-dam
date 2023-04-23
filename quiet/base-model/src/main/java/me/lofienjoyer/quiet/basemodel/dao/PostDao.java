package me.lofienjoyer.quiet.basemodel.dao;

import me.lofienjoyer.quiet.basemodel.entity.Post;
import me.lofienjoyer.quiet.basemodel.entity.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for {@link me.lofienjoyer.quiet.basemodel.entity.Post} entities
 */
public interface PostDao extends JpaRepository<Post, Long> {

    List<Post> findByProfileOrderByDateDesc(Profile profile);

    @Query(value = "SELECT p FROM Post p LEFT JOIN FETCH p.profile LEFT JOIN FETCH p.likes WHERE p.id IN :idsList ORDER BY p.date DESC")
    List<Post> findByIdIn(@Param("idsList") List<Long> idsList);

    @Query(value = "SELECT p.id FROM Post p WHERE p.profile IN (SELECT pr.following FROM Profile pr WHERE pr.id = :id) OR p.profile IN (SELECT pr FROM Profile pr WHERE pr.id = :id) ORDER BY p.date DESC")
    List<Long> getPostsIdsFromFollowed(@Param("id") long profileId, Pageable pageable);

}
