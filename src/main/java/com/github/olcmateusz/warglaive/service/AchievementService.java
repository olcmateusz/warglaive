package com.github.olcmateusz.warglaive.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.olcmateusz.warglaive.domain.Achievement;
import com.github.olcmateusz.warglaive.repository.AchievementRepository;

@Service
public class AchievementService {
	
	@Autowired
	AchievementRepository achievementRepo;
	
	public Achievement getAchievement(Achievement achievement) {
		
		Optional<Achievement> existingAchievement = achievementRepo.findByName(achievement.getName());
		return existingAchievement.orElse(achievement);
	}
	
	public void save(Achievement achievement) {
		achievementRepo.save(achievement);
	}
	
}
