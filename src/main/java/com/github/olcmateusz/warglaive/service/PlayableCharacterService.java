package com.github.olcmateusz.warglaive.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.olcmateusz.warglaive.domain.PlayableCharacter;
import com.github.olcmateusz.warglaive.domain.Player;
import com.github.olcmateusz.warglaive.repository.PlayableCharacterRepository;

@Service
public class PlayableCharacterService {

	@Autowired
	PlayableCharacterRepository playableCharacterRepo;
	
	public PlayableCharacter getPlayableCharacter(PlayableCharacter playableCharacter){
		Optional<PlayableCharacter> playerInQuestion = playableCharacterRepo.findByName(playableCharacter.getName());
		return playerInQuestion.orElse(playableCharacter);
	}
}
