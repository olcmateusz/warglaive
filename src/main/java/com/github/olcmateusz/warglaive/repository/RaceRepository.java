package com.github.olcmateusz.warglaive.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.olcmateusz.warglaive.domain.Race;

public interface RaceRepository extends JpaRepository<Race, Long>{
	
	Optional<Race> findByName(String name);

}
