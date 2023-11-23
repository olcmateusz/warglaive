package com.github.olcmateusz.warglaive.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class OAuthClientConfiguration {
	
	@Autowired
	BlizzardConfigProperties blizzConfig;

    @Bean
    ReactiveClientRegistrationRepository clientRegistrations(
//            @Value("${spring.security.blizzard.client.provider.blizzard.token-uri}") String token_uri,
//            @Value("${spring.security.blizzard.client.registration.blizzard.client-id}") String client_id,
//            @Value("${spring.security.blizzard.client.registration.blizzard.client-secret}") String client_secret,
//            @Value("${spring.security.blizzard.client.registration.blizzard.scope}") String scope,
//            @Value("${spring.security.blizzard.client.registration.blizzard.authorization-grant-type}") String authorizationGrantType

    ) {
        ClientRegistration registration = ClientRegistration
                .withRegistrationId("blizzard")
                .tokenUri("https://oauth.battle.net/token")
//                .clientId("07a3336a2e8449b6b8441a1574363694")
                .clientId(blizzConfig.clientId())
//                .clientSecret("w16OqRKsAv4r354LNGnbLmOalxzAiLzi")
                .clientSecret(blizzConfig.clientSecret())
//                .scope("wow.profile")
                .authorizationGrantType(new AuthorizationGrantType("client_credentials"))
                .build();
        return new InMemoryReactiveClientRegistrationRepository(registration);
    }

    @Bean
    WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations) {
        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
            .build();
        InMemoryReactiveOAuth2AuthorizedClientService clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations);
        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations, clientService);
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth.setDefaultClientRegistrationId("blizzard");
        return WebClient.builder()
        		.exchangeStrategies(strategies)
        		.baseUrl("https://eu.api.blizzard.com")
                .filter(oauth)
                .build();
    }

}