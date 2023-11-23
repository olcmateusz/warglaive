package com.github.olcmateusz.warglaive.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("blizzard")
public record BlizzardConfigProperties(String clientId, String clientSecret) {
		

}
