package me.lofienjoyer.quiet.postservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan("me.lofienjoyer.quiet.*")
@EntityScan("me.lofienjoyer.quiet.*")
class PostServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
