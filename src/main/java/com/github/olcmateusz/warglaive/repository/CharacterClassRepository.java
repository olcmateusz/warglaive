package com.github.olcmateusz.warglaive.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.olcmateusz.warglaive.domain.CharacterClass;

public interface CharacterClassRepository extends JpaRepository<CharacterClass, Long>{

	CharacterClass findByName(String name);
}
