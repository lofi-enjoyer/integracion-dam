package me.lofienjoyer.quiet.auth;

import me.lofienjoyer.quiet.auth.tests.IntegrationTestsProfile;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@DataJpaTest
@ContextConfiguration(
		classes = { IntegrationTestsProfile.class },
		loader = AnnotationConfigContextLoader.class)
@ComponentScan("me.lofienjoyer.quiet.*")
@EntityScan("me.lofienjoyer.quiet.basemodel.*")
@ActiveProfiles("test")
class AuthServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
