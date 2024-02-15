package com.github.olcmateusz.warglaive.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.olcmateusz.warglaive.domain.Achievement;

public interface AchievementRepository extends JpaRepository<Achievement, Long>{

	Optional<Achievement> findByName(String name);

}
