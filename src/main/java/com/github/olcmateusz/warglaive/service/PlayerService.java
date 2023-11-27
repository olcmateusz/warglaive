package com.github.olcmateusz.warglaive.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.olcmateusz.warglaive.domain.Player;
import com.github.olcmateusz.warglaive.repository.PlayerRepository;

@Service
public class PlayerService {

	@Autowired
	private PlayerRepository playerRepo;
	
	public void save(Player player) {
		playerRepo.save(player);
	}
	
	public Player getPlayer(Player player) {
		Optional <Player> playerInQuestion = playerRepo.findByRegionAndBracketAndCharacter_Name(player.getRegion(), player.getBracket(), player.getCharacter().getName());
		return playerInQuestion.orElse(player);
	}
	
	public Player getPlayer(long id) {
		return null;
	}
	
	public List<Player> getPlayersByRegionAndBracket(String region, String bracket){
		
		return playerRepo.findByRegionAndBracket(region, bracket);
		
	}
}
