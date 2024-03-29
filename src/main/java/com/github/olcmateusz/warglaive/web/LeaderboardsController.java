package com.github.olcmateusz.warglaive.web;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.olcmateusz.warglaive.domain.CharacterClass;
import com.github.olcmateusz.warglaive.domain.LeaderboardsResponse;
import com.github.olcmateusz.warglaive.domain.PlayableCharacter;
import com.github.olcmateusz.warglaive.domain.Player;
import com.github.olcmateusz.warglaive.domain.PvPRewardsResponse;
import com.github.olcmateusz.warglaive.domain.Race;
import com.github.olcmateusz.warglaive.domain.Realm;
import com.github.olcmateusz.warglaive.domain.Reward;
import com.github.olcmateusz.warglaive.domain.Statistic;
import com.github.olcmateusz.warglaive.service.BracketService;
import com.github.olcmateusz.warglaive.service.CharacterClassService;
import com.github.olcmateusz.warglaive.service.PlayableCharacterService;
import com.github.olcmateusz.warglaive.service.PlayerService;
import com.github.olcmateusz.warglaive.service.RaceService;
import com.github.olcmateusz.warglaive.service.RealmService;
import com.github.olcmateusz.warglaive.service.RewardService;
import com.github.olcmateusz.warglaive.service.StatisticService;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;



@Controller
@RequestMapping("/leaderboards")
public class LeaderboardsController {
	

    private WebClient euWebClient;
    private WebClient usWebClient;
    private RaceService raceService;
    private CharacterClassService characterClassService;
    private RealmService realmService;
    private StatisticService statisticService;
    private PlayerService playerService;
    private PlayableCharacterService playableCharacterService;
    private RewardService rewardService;
    private BracketService bracketService;

//    private static final double REQUEST_PER_SECOND = 95.0;
//    private final RateLimiter rateLimiter = RateLimiter.create(REQUEST_PER_SECOND);


	public LeaderboardsController(WebClient usWebClient, WebClient euWebClient,
				RaceService raceService, CharacterClassService characterClassService, RealmService realmService,StatisticService statisticService,
				PlayerService playerService,PlayableCharacterService playableCharacterService, RewardService rewardService, BracketService bracketService) {
			this.euWebClient = euWebClient;
			this.usWebClient = usWebClient;
			this.raceService = raceService;
			this.characterClassService = characterClassService;
			this.realmService = realmService;
			this.statisticService = statisticService;
			this.playerService = playerService;
			this.playableCharacterService = playableCharacterService;
			this.rewardService = rewardService;
			this.bracketService = bracketService;
		}

	@GetMapping(value ={"update", "update/{pathRegion}/{pathBracket}", "update/{pathRegion}/{pathBracket}/{pvpSeason}" })
	public String update(@PathVariable Optional<String> pathRegion, @PathVariable Optional<String> pathBracket,@PathVariable Optional<String>pathSeason,  ModelMap model) {
		String leaderboardsPath = "/data/wow";
		String profilePath = "/profile/wow/character";
		String region = pathRegion.isPresent() ? pathRegion.get().toUpperCase() : "EU";
		String bracket = pathBracket.isPresent() ? pathBracket.get() : "3v3";
		//Seasons different than 8 are currently not supported
		String pvpSeason = pathSeason.isPresent() ? pathBracket.get() : "8";
		String namespaceLeaderboard;
		String namespaceProfile;
		String locale;
		String pvpRegion;
		WebClient client;

		
		if (region.equals("EU")) {
			namespaceLeaderboard = "dynamic-classic-eu";
			namespaceProfile = "profile-classic-eu";
			locale = "en_GB";
			pvpRegion = "0";
			client = euWebClient;
		}else{
			namespaceLeaderboard = "dynamic-classic-us";
			namespaceProfile = "profile-classic-us";
			locale = "en_US";
			pvpRegion = "1";
			client = usWebClient;
			}
		
		System.out.println(region);
		System.out.println(bracket);
		System.out.println(pvpSeason);
		
		//Figure out if all races are present
		raceService.addAllRacesIfEmpty();
		//figure out if all character_classes are present
		characterClassService.addAllClassesIfEmpty();
		

		UriComponents uriComponents = UriComponentsBuilder.newInstance()
			    .path(leaderboardsPath)
			    .pathSegment("pvp-region", pvpRegion, "pvp-season", pvpSeason, "pvp-reward", "index")
			    .queryParam("namespace", namespaceLeaderboard)
			    .queryParam("locale", locale)
			    .build();

		System.out.println("Request URI: " + uriComponents.toUriString()); // Log the URI
		
		PvPRewardsResponse pvpResponse = client.get()
				.uri(uriBuilder -> uriBuilder
						.path(leaderboardsPath)
						.pathSegment("pvp-region", pvpRegion,"pvp-season", pvpSeason,"pvp-reward","index")
					    .queryParam("namespace", namespaceLeaderboard)
					    .queryParam("locale", locale)
					    .build())
					  .retrieve()
					  .bodyToMono(PvPRewardsResponse.class)
					  .block();

		pvpResponse.getRewards();
		
		for(Reward reward : pvpResponse.getRewards()) {
			rewardService.saveReward(reward);
		}
		
		
		 
		////////////////////////
		LeaderboardsResponse response = client.get()
				.uri(uriBuilder -> uriBuilder
						.path(leaderboardsPath)
						.pathSegment("pvp-region", pvpRegion,"pvp-season", pvpSeason,"pvp-leaderboard",bracket)
					    .queryParam("namespace", namespaceLeaderboard)
					    .build())
					  .retrieve()
					  .bodyToMono(LeaderboardsResponse.class)
					  .block();
		
		response.getEntries();


		for (Player player : response.getEntries()) {
			PlayableCharacter playableCharacterAdditionalInfo = client.get()
					.uri(uriBuilder -> uriBuilder
							.path(profilePath)
							.pathSegment(player.getCharacter().getRealm().getSlug(), player.getCharacter().getName().toLowerCase())
							.queryParam("namespace", namespaceProfile)
							.queryParam("locale", locale)
							.build())
					.retrieve()
					.bodyToMono(PlayableCharacter.class)
					.onErrorResume(WebClientResponseException.class, ex -> ex.getStatusCode() == ResponseEntity.notFound().build().getStatusCode() ? Mono.empty() : Mono.error(ex))
	                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(10))
	                        .filter(ex -> ex instanceof WebClientResponseException &&
	                                ((WebClientResponseException) ex).getStatusCode().is5xxServerError())
	                )
					.block();

			
			if(null != playableCharacterAdditionalInfo) {
				
				//Get player with ID from DB
				player.setBracket(bracket);
				player.setRegion(region);
				Player myPlayer = playerService.getPlayer(player);
				
				//save Extended playableCharaceter
				PlayableCharacter myCharacter = playableCharacterService.getPlayableCharacter(myPlayer.getCharacter());
				
				//Ovverride blizzard race with mine
				Race race = raceService.getRaceByName(playableCharacterAdditionalInfo.getRace().getName());
				myCharacter.setRace(race);
				
				//override blizzard Character_class with mine 
				CharacterClass characterClass = characterClassService.getCharacterClassByName(playableCharacterAdditionalInfo.getCharacter_class().getName());
				myCharacter.setCharacter_class(characterClass);
				
				//override blizzard realm with my own
				realmService.saveIfNew(playableCharacterAdditionalInfo.getRealm());
				Realm realm = realmService.getRealmByName(playableCharacterAdditionalInfo.getRealm().getSlug());
				myCharacter.setRealm(realm);
				
				playableCharacterService.save(myCharacter);
				
				//save playableCharacterAdditionalInfo
				myPlayer.setCharacter(myCharacter);

				
				//add Region to player
				myPlayer.setRegion(region);
				
				//add Bracket to player
				myPlayer.setBracket(bracket);
				myPlayer.setBracketFull(bracketService.getBracket(response.getBracket()));
				
				//save Statistics
				Statistic myStats = statisticService.getStatistics(myPlayer.getSeason_match_statistics());
				statisticService.save(myStats);
				//save player
				playerService.save(myPlayer);
				
				System.out.println("breakpoint");
				
						
			}

		}
		return "leaderboards";
	}
	
	@GetMapping(value = {"", "/{pathRegion}/{pathBracket}","/{pathRegion}/{pathBracket}/{page}","/{pathRegion}/{pathBracket}/{pathSeason}/{page}"})
	public String showLeaderboards(
			@PathVariable Optional<String> pathRegion, 
			@PathVariable Optional<String> pathBracket,
			@PathVariable Optional<String> pathSeason, 
			@PathVariable Optional<Integer> page,
			ModelMap model) {
		
		String region = pathRegion.isPresent() ? pathRegion.get().toUpperCase() : "EU";
		String bracket = pathBracket.isPresent() ? pathBracket.get() : "3v3";
		int currentPage = page.isPresent() ? page.get() : 1;
		int pageSize = 100;
		

		PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize, Sort.by("rank"));
		
		Page<Player> playerPage = playerService.getPlayersByRegionAndBracketPaginated(region, bracket, pageRequest);
		List<Player> players = playerPage.getContent();

		
		model.put("leaderboards", players);
		model.addAttribute("region", region);
		model.addAttribute("bracket", bracket);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", playerPage.getTotalPages());
		
		return "leaderboards";
	}

}
