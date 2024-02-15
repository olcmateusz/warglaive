package com.github.olcmateusz.warglaive.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.olcmateusz.warglaive.domain.Bracket;
import com.github.olcmateusz.warglaive.repository.BracketRepository;

@Service
public class BracketService {

	@Autowired
	BracketRepository bracketRepo;
	
	public Bracket getBracket(Bracket bracket) {
		Optional <Bracket> existingBracket = bracketRepo.findByType(bracket.getType());
		
		//Probably worst line of code i ever wrote
		//Response Id starts with 0, my ids starts with 1 
		//resulted in 2nd element of response overriding 1st element in DB
		if(existingBracket.isEmpty()) bracket.setId(bracket.getId() + 1);
		
		return existingBracket.orElse(bracket);
	}
	
	public void save(Bracket bracket) {
		bracketRepo.save(bracket);
	}
}
