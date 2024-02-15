package com.github.olcmateusz.warglaive.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.olcmateusz.warglaive.domain.Reward;

public interface RewardRepository extends JpaRepository<Reward, Long>{

	Optional<Reward> findByAchievementIdAndBracketId(long achievementId, long bracketId);

}
