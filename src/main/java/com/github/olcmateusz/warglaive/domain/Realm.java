package com.github.olcmateusz.warglaive.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Realm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String slug;
	private long blizzard_id;
	
	public Realm() {
		
	}
	
	public Realm(String slug, long blizzard_id) {
		this.slug = slug;
		this.blizzard_id = blizzard_id;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public long getBlizzard_id() {
		return blizzard_id;
	}
	public void setBlizzard_id(long blizzard_id) {
		this.blizzard_id = blizzard_id;
	}
	
}
