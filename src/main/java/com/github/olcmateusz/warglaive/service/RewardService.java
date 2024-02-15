package com.github.olcmateusz.warglaive.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.olcmateusz.warglaive.domain.Reward;
import com.github.olcmateusz.warglaive.repository.RewardRepository;

@Service
public class RewardService {
	
	@Autowired
	RewardRepository rewardRepo;
	@Autowired
	AchievementService achievementService;
	@Autowired
	BracketService bracketService;

	public void saveReward(Reward reward) {
		reward.setAchievement(achievementService.getAchievement(reward.getAchievement()));
		reward.setBracket(bracketService.getBracket(reward.getBracket()));
		
		
		achievementService.save(reward.getAchievement());
		bracketService.save(reward.getBracket());
		
		Reward rewardUpdated = getReward(reward);
		rewardUpdated.setRating_cutoff(reward.getRating_cutoff());
		rewardRepo.save(rewardUpdated);
	}
	
	public Reward getReward(Reward reward) {
		Optional <Reward> existingReward = rewardRepo.findByAchievementIdAndBracketId(reward.getAchievement().getId(), reward.getBracket().getId());
		
		return existingReward.orElse(reward);																																

	}
}
