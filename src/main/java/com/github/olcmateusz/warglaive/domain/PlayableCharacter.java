package com.github.olcmateusz.warglaive.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PlayableCharacter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private long blizzard_id;
	@ManyToOne
	@JoinColumn(name="realm_id")
	private Realm realm;
	
	public PlayableCharacter() {
		
	}
	
	public PlayableCharacter(String name, long blizzard_id, Realm realm) {
		this.name = name;
		this.blizzard_id = blizzard_id;
		this.realm = realm;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getBlizzard_id() {
		return blizzard_id;
	}

	public void setBlizzard_id(long blizzard_id) {
		this.blizzard_id = blizzard_id;
	}

	public Realm getRealm() {
		return realm;
	}

	public void setRealm(Realm realm) {
		this.realm = realm;
	}

	@Override
	public String toString() {
		return "PlayableCharacter [id=" + id + ", name=" + name + ", blizzard_id=" + blizzard_id + ", realm=" + realm
				+ "]";
	}
	
	
	
}
