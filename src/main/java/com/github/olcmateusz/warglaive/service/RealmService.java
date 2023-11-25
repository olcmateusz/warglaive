package com.github.olcmateusz.warglaive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.olcmateusz.warglaive.domain.Realm;
import com.github.olcmateusz.warglaive.repository.RealmRepository;

@Service
public class RealmService {

	@Autowired
	private RealmRepository realmRepo;
	
	public Realm saveIfNew(Realm realm) {
		Realm existing = realmRepo.findByName(realm.getName());
		if(existing == null){
			return realmRepo.save(realm);			
		}
		return existing;
	}
	
	public Realm getRealmByName(String name) {
		return realmRepo.findBySlug(name.toLowerCase());
	}
	
}
