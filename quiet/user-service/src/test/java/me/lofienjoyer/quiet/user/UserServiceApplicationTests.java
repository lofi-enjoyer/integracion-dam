package me.lofienjoyer.quiet.user;

import jakarta.annotation.Resource;
import me.lofienjoyer.quiet.basemodel.dao.ProfileDao;
import me.lofienjoyer.quiet.basemodel.dao.UserInfoDao;
import me.lofienjoyer.quiet.basemodel.entity.Profile;
import me.lofienjoyer.quiet.basemodel.entity.UserInfo;
import me.lofienjoyer.quiet.baseservice.tests.IntegrationTestsProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@DataJpaTest
@ContextConfiguration(
		classes = { IntegrationTestsProfile.class },
		loader = AnnotationConfigContextLoader.class)
@ComponentScan("me.lofienjoyer.quiet.*")
@EntityScan("me.lofienjoyer.quiet.basemodel.*")
@ActiveProfiles("test")
class UserServiceApplicationTests {

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private ProfileDao profileDao;

	@Test
	void testUserSave() {
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail("user@email.com");
		userInfoDao.save(userInfo);

		Assertions.assertEquals(1, userInfoDao.count());
	}

	@Test
	void testProfiles() {
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail("test@email.com");

		Profile profile = new Profile();
		profile.setUsername("test");

		profileDao.save(profile);

		userInfo.setProfile(profile);

		userInfoDao.save(userInfo);

		Assertions.assertEquals(userInfoDao.count(), 1);
		Assertions.assertEquals(profileDao.count(), 1);
	}

}
