package com.github.olcmateusz.warglaive.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

import com.github.olcmateusz.warglaive.domain.LeaderboardsResponse;
import com.github.olcmateusz.warglaive.domain.PlayableCharacter;
import com.github.olcmateusz.warglaive.domain.Player;
import com.github.olcmateusz.warglaive.domain.Realm;
import com.github.olcmateusz.warglaive.domain.Statistic;
import com.github.olcmateusz.warglaive.enums.Faction;
import com.github.olcmateusz.warglaive.repository.PlayableCharacterRepository;
import com.github.olcmateusz.warglaive.repository.PlayerRepository;
import com.github.olcmateusz.warglaive.repository.RealmRepository;
import com.github.olcmateusz.warglaive.repository.StatisticRepository;



@Controller
@RequestMapping("/leaderboards")
public class LeaderboardsController {
	
    private WebClient webClient;
    private PlayerRepository playerRepo;
    private PlayableCharacterRepository playableCharacterRepo;
    private RealmRepository realmRepo;
    private StatisticRepository StatisticRepo;

    


	public LeaderboardsController(WebClient webClient, PlayerRepository playerRepo,
				PlayableCharacterRepository playableCharacterRepo, RealmRepository realmRepo,
				StatisticRepository statisticRepo) {
			this.webClient = webClient;
			this.playerRepo = playerRepo;
			this.playableCharacterRepo = playableCharacterRepo;
			this.realmRepo = realmRepo;
			StatisticRepo = statisticRepo;
		}


	@GetMapping("{region}/{bracket}")
	public String params(@PathVariable String region, @PathVariable String bracket) {
		System.out.println(region + " " + bracket);
		return "leaderboards";
	}
	
	
	
	@GetMapping(value = "/update")
	public String printSecret(ModelMap model) {

		
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

	@GetMapping("/get")
	public LeaderboardsResponse printSecret2(ModelMap model) {

		
	return webClient.get()
			.uri(uriBuilder -> uriBuilder
				    .path("/data/wow/pvp-region/0/pvp-season/8/pvp-leaderboard/3v3")
				    .queryParam("namespace", "dynamic-classic-eu")
				    .build())
				  .retrieve()
				  .bodyToMono(LeaderboardsResponse.class)
				  .block();
		
//		model.put("leaderboards", response.getEntries());
		
//		playerRepo.saveAll(response.getEntries());
//		
//		return "leaderboards";
	}

}
