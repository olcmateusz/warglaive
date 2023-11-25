package com.github.olcmateusz.warglaive.web;

import java.time.Duration;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.github.olcmateusz.warglaive.domain.CharacterClass;
import com.github.olcmateusz.warglaive.domain.LeaderboardsResponse;
import com.github.olcmateusz.warglaive.domain.PlayableCharacter;
import com.github.olcmateusz.warglaive.domain.Player;
import com.github.olcmateusz.warglaive.domain.Race;
import com.github.olcmateusz.warglaive.domain.Realm;
import com.github.olcmateusz.warglaive.domain.Statistic;
import com.github.olcmateusz.warglaive.enums.Faction;
import com.github.olcmateusz.warglaive.repository.PlayableCharacterRepository;
import com.github.olcmateusz.warglaive.repository.PlayerRepository;
import com.github.olcmateusz.warglaive.repository.RealmRepository;
import com.github.olcmateusz.warglaive.repository.StatisticRepository;
import com.github.olcmateusz.warglaive.service.CharacterClassService;
import com.github.olcmateusz.warglaive.service.RaceService;
import com.github.olcmateusz.warglaive.service.RealmService;
import com.github.olcmateusz.warglaive.service.StatisticService;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;



@Controller
@RequestMapping("/leaderboards")
public class LeaderboardsController {
	
    private WebClient webClient;
    private PlayerRepository playerRepo;
    private PlayableCharacterRepository playableCharacterRepo;
    private RealmRepository realmRepo;
    private StatisticRepository StatisticRepo;
    private RaceService raceService;
    private CharacterClassService characterClassService;
    private RealmService realmService;
    private StatisticService statisticService;

//    private static final double REQUEST_PER_SECOND = 95.0;
//    private final RateLimiter rateLimiter = RateLimiter.create(REQUEST_PER_SECOND);


	public LeaderboardsController(WebClient webClient, PlayerRepository playerRepo,
				PlayableCharacterRepository playableCharacterRepo, RealmRepository realmRepo,
				RaceService raceService, CharacterClassService characterClassService, RealmService realmService,StatisticService statisticService, StatisticRepository statisticRepo) {
			this.webClient = webClient;
			this.playerRepo = playerRepo;
			this.playableCharacterRepo = playableCharacterRepo;
			this.realmRepo = realmRepo;
			this.raceService = raceService;
			this.characterClassService = characterClassService;
			this.realmService = realmService;
			this.statisticService = statisticService;
			StatisticRepo = statisticRepo;
		}

	@GetMapping(value ={"update", "update/{pathRegion}/{pathBracket}" })
	public String update(@PathVariable Optional<String> pathRegion, @PathVariable Optional<String> pathBracket, ModelMap model) {
		String leaderboardsPath;
		String region = pathRegion.isPresent() ? pathRegion.get().toUpperCase() : "EU";
		String bracket = pathBracket.isPresent() ? pathBracket.get() : "3v3";
		String namespaceLeaderboard;
		String namespaceProfile;
		String locale;
		namespaceLeaderboard = region.equals("EU") ? "dynamic-classic-eu" : "dynamic-classic-us";
		namespaceProfile = region.equals("EU") ? "profile-classic-eu" : "profile-classic-us";
		locale = region.equals("EU") ? "en_GB" : "en_US";
		
		System.out.println(region);
		System.out.println(bracket);
		
		//Figure out if all races are present
		raceService.addAllRacesIfEmpty();
		//figure out if all character_classes are present
		characterClassService.addAllClassesIfEmpty();
		
		
		LeaderboardsResponse response = webClient.get()
				.uri(uriBuilder -> uriBuilder
					    .path("/data/wow/pvp-region/0/pvp-season/8/pvp-leaderboard")
					    .path("/" + bracket)
					    .queryParam("namespace", namespaceLeaderboard)
					    .build())
					  .retrieve()
					  .bodyToMono(LeaderboardsResponse.class)
					  .block();
		
		response.getEntries();

//		Map<String,PlayableCharacter> testMap = new HashMap<>();
		for (Player player : response.getEntries()) {
			PlayableCharacter playableCharacterAdditionalInfo = webClient.get()
					.uri(uriBuilder -> uriBuilder
							.path("/profile/wow/character")
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
//			System.out.println(player.getCharacter().toString());
			
			if(null != playableCharacterAdditionalInfo) {
				//Ovverride blizzard race with mine
				Race race = raceService.getRaceByName(playableCharacterAdditionalInfo.getRace().getName());
				playableCharacterAdditionalInfo.setRace(race);

				//override blizzard Character_class with mine 
				CharacterClass characterClass = characterClassService.getCharacterClassByName(playableCharacterAdditionalInfo.getCharacter_class().getName());
				playableCharacterAdditionalInfo.setCharacter_class(characterClass);
				
				//save Realm
				//TODO check if realm exists
				realmService.saveIfNew(playableCharacterAdditionalInfo.getRealm());
				Realm realm = realmService.getRealmByName(playableCharacterAdditionalInfo.getRealm().getSlug());
				playableCharacterAdditionalInfo.setRealm(realm);
				
				System.out.println("Player: " + player.toString());
				//save playableCharacterAdditionalInfo
				player.setCharacter(playableCharacterAdditionalInfo);
				System.out.println("Player with set additionals: " + player.toString());
				//add Region to player
				player.setRegion(region);
				//add Bracket to player
				player.setBracket(bracket);
				System.out.println("Player with bracket and region: " + player.toString());
				//save Extended playableCharaceter
				playableCharacterRepo.save(playableCharacterAdditionalInfo);
				//save Statistics
				statisticService.save(player.getSeason_match_statistics());
				//save player
				playerRepo.save(player);
				
				System.out.println("Player after saving: " + player.toString());
				System.out.println("NEXT!");
				
						
//				System.out.println("Values race:" + playableCharacterAdditionalInfo.getRace());
//				System.out.println("Player race:" + player.getCharacter().getRace());
//				System.out.println("Player:" + player.toString());
//				raceService.saveIfNotExist(variable.getRace());
//				variable.setRace(values.getRace());
//				player.getCharacter().setRace(values.getRace());
//				testMap.put(player.getCharacter().getName(), values);
						
			}

		}
		return "leaderboards";
	}
	
	@GetMapping(value ={ "", "{pathRegion}/{pathBracket}" })
	public String printSecret(@PathVariable Optional<String> pathRegion, @PathVariable Optional<String> pathBracket, ModelMap model) {
		
		String region = pathRegion.isPresent() ? pathRegion.get() : "EU";
		String bracket = pathBracket.isPresent() ? pathBracket.get() : "3v3";
		
		System.out.println(region);
		System.out.println(bracket);
		
		LeaderboardsResponse response = webClient.get()
			.uri(uriBuilder -> uriBuilder
				    .path("/data/wow/pvp-region/0/pvp-season/8/pvp-leaderboard/3v3")
				    .queryParam("namespace", "dynamic-classic-eu")
				    .build())
				  .retrieve()
				  .bodyToMono(LeaderboardsResponse.class)
				  .block();
		
		model.put("leaderboards", response.getEntries());
		
		System.out.println(response.getEntries().get(15).getCharacter().toString());
		
//		playerRepo.saveAll(response.getEntries());
//		
		return "leaderboards";
//
//
	}

	@GetMapping(value="test")
	public String testowe(){
		Statistic stats = new Statistic(10,9,1);
		StatisticRepo.save(stats);
		Realm realm = new Realm("firemaw",15);
		realmRepo.save(realm);
		Faction faction = Faction.ALLIANCE;
		PlayableCharacter character = new PlayableCharacter("robert", 105, realm);
		playableCharacterRepo.save(character);
		Player player = new Player(character,faction,1,1500,stats);
		
		playerRepo.save(player);
		
		return "leaderboards";
		
	}


}
