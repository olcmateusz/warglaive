package com.github.olcmateusz.warglaive.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.github.olcmateusz.warglaive.domain.PlayableCharacter;

public interface PlayableCharacterRepository extends JpaRepository<PlayableCharacter, Long>{
	
	Optional <PlayableCharacter> findById(long id);
	Optional <PlayableCharacter> findByName(String name);
	
	@Query("SELECT pc.name FROM PlayableCharacter pc")
	List<String> findAllNames();
	
}
