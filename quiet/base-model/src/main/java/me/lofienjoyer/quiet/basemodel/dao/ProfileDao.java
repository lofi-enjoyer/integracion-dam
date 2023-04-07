package me.lofienjoyer.quiet.basemodel.dao;

import me.lofienjoyer.quiet.basemodel.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileDao extends JpaRepository<Profile, Long> {
}
