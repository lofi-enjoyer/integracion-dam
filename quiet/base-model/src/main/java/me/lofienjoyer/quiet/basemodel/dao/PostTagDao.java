package me.lofienjoyer.quiet.basemodel.dao;

import me.lofienjoyer.quiet.basemodel.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagDao extends JpaRepository<PostTag, Long> {
}
