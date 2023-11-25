package com.github.olcmateusz.warglaive.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.olcmateusz.warglaive.domain.Race;
import com.github.olcmateusz.warglaive.repository.RaceRepository;


@Service
public class RaceService {
	
	@Autowired
	private RaceRepository raceRepo;

//	public Optional<Race> findByName(String name) {
//		return raceRepo.findByName(name);
//	}
	
	public Race getRaceByName(String name) {
		return raceRepo.findByName(name);
	}
	
	
	public void addAllRacesIfEmpty() {
		List<Race>races = raceRepo.findAll();
		if(races.size() < 10) {
			List<String> allRaces = Arrays.asList("Human","Dwarf","Gnome","Night Elf","Draenei","Orc","Troll","Tauren","Undead","Blood Elf");
			for (String race : allRaces) {
				Race newRace = new Race(race);
				raceRepo.save(newRace);
			}
		}
	}
}
