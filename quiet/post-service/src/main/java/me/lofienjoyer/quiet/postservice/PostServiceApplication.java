package me.lofienjoyer.quiet.postservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Post service main class
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("me.lofienjoyer.quiet.*")
@EntityScan("me.lofienjoyer.quiet.basemodel.*")
@EnableJpaRepositories("me.lofienjoyer.quiet.*")
public class PostServiceApplication {

	/**
	 * Starts the service
	 * @param args Startup arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(PostServiceApplication.class, args);
	}

}
