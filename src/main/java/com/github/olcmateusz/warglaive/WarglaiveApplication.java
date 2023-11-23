package com.github.olcmateusz.warglaive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.github.olcmateusz.warglaive.config.BlizzardConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties(BlizzardConfigProperties.class)
public class WarglaiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarglaiveApplication.class, args);
	}

}
