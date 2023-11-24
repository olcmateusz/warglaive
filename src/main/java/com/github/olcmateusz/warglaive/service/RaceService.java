package com.github.olcmateusz.warglaive.service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.olcmateusz.warglaive.domain.Race;
import com.github.olcmateusz.warglaive.repository.RaceRepository;


@Service
public class RaceService {
	
	@Autowired
	private RaceRepository raceRepo;

	public Optional<Race> finduserByUsername(String name) {
		return raceRepo.findByName(name);
	}
}
