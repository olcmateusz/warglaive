package com.github.olcmateusz.warglaive.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.olcmateusz.warglaive.domain.Statistic;
import com.github.olcmateusz.warglaive.repository.StatisticRepository;

@Service
public class StatisticService {

	@Autowired
	private StatisticRepository statisticRepo;
	
	public void save(Statistic statistic) {
		statisticRepo.save(statistic);
	}
//	public Player getPlayer(Player player) {
//		Optional <Player> playerInQuestion = playerRepo.findByRegionAndBracketAndCharacter_Name(player.getRegion(), player.getBracket(), player.getCharacter().getName());
//		return playerInQuestion.orElse(player);
//	}
	public Statistic getStatistics(Statistic statistics) {
		Optional<Statistic> myStats = statisticRepo.findById(statistics.getId());
		return myStats.orElse(statistics);
	}
}
