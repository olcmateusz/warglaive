package com.github.olcmateusz.warglaive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.github.olcmateusz.warglaive.config.BlizzardConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties(BlizzardConfigProperties.class)
@EnableScheduling
public class WarglaiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarglaiveApplication.class, args);
	}

}
