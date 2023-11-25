package com.github.olcmateusz.warglaive.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.olcmateusz.warglaive.domain.Realm;

public interface RealmRepository extends JpaRepository<Realm, Long>{

	Realm findBySlug(String name);
	Realm findByName(String name);
}
