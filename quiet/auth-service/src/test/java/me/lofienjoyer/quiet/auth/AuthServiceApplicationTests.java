package me.lofienjoyer.quiet.auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;

@SpringBootTest
@ComponentScan("me.lofienjoyer.quiet.*")
@EntityScan("me.lofienjoyer.quiet.*")
class AuthServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
