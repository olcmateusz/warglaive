package com.github.olcmateusz.warglaive.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.olcmateusz.warglaive.domain.Bracket;

public interface BracketRepository extends JpaRepository<Bracket, Long>{

	Optional <Bracket> findByType(String type);


}
