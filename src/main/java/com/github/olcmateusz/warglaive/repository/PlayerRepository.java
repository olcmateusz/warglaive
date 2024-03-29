package com.github.olcmateusz.warglaive.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.olcmateusz.warglaive.domain.PlayableCharacter;
import com.github.olcmateusz.warglaive.domain.Player;

public interface PlayerRepository extends JpaRepository<Player, Long>{
	
	Player findById(long id);
	Player findByCharacter_IdAndBracketAndRegion(PlayableCharacter character, String bracket, String region);
	Optional <Player> findByRegionAndBracketAndCharacter_Name(String region, String bracket, String name);
	
	List<Player> findByRegionAndBracket(String region, String bracket);
	
	
    Page<Player> findByRegionAndBracket(String region, String bracket, Pageable pageable);
	
}
