package com.github.olcmateusz.warglaive.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.olcmateusz.warglaive.domain.CharacterClass;
import com.github.olcmateusz.warglaive.repository.CharacterClassRepository;

@Service
public class CharacterClassService {
	
	@Autowired
	private CharacterClassRepository characterClassRepo;
	
	public CharacterClass getCharacterClassByName(String name) {
		return characterClassRepo.findByName(name);
	}
	
	public void addAllClassesIfEmpty() {
		List<CharacterClass>classes = characterClassRepo.findAll();
		if(classes.size() < 10) {
			List<String> allClasses = Arrays.asList("Warrior","Rogue","Druid","Death Knight","Paladin","Warlock","Mage","Shaman","Priest","Hunter");
			for (String characterClass : allClasses) {
				CharacterClass newClass = new CharacterClass(characterClass);
				characterClassRepo.save(newClass);
			}
		}
	}
}


