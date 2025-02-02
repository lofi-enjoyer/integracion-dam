package me.lofienjoyer.quiet.basemodel.dao;

import jakarta.transaction.Transactional;
import me.lofienjoyer.quiet.basemodel.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostTagDao extends JpaRepository<PostTag, Long> {

}
