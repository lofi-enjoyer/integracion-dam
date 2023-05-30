package me.lofienjoyer.quiet.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Auth service main class
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("me.lofienjoyer.quiet.*")
@EntityScan("me.lofienjoyer.quiet.*")
@EnableJpaRepositories("me.lofienjoyer.quiet.*")
public class AuthServiceApplication {

	/**
	 * Starts the service
	 * @param args Startup arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
