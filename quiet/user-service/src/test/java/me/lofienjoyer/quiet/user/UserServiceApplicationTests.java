package me.lofienjoyer.quiet.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest
@ComponentScan("me.lofienjoyer.quiet.*")
@EntityScan("me.lofienjoyer.quiet.basemodel.*")
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
