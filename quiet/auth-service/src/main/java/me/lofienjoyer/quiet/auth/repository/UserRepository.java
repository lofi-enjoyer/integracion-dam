package me.lofienjoyer.quiet.auth.repository;

import me.lofienjoyer.quiet.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
