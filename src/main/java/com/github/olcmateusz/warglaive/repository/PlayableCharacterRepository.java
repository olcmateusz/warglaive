package com.github.olcmateusz.warglaive.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.olcmateusz.warglaive.domain.PlayableCharacter;

public interface PlayableCharacterRepository extends JpaRepository<PlayableCharacter, Long>{

}
