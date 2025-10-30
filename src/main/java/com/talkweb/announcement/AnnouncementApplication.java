package com.talkweb.announcement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class AnnouncementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnnouncementApplication.class, args);
	}

}
