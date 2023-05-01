package me.lofienjoyer.quiet.media;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("me.lofienjoyer.quiet.*")
@EntityScan("me.lofienjoyer.quiet.basemodel.*")
@EnableJpaRepositories("me.lofienjoyer.quiet.*")
public class MediaServiceApplication {

	@Value("${quiet.profile-img-dir}")
	private String profileImgDir;

	public static void main(String[] args) {
		SpringApplication.run(MediaServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return (args) -> {
			try {
				Files.createDirectory(Paths.get(profileImgDir));
			} catch (IOException e) {}
		};
	}

}
