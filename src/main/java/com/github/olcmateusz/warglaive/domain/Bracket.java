package com.github.olcmateusz.warglaive.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@JsonIgnoreProperties
public class Bracket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	public String type;
	
	public Bracket() {
	}
	
	public Bracket(String type) {
		this.type = type;
	}

	public Bracket(long id, String type) {
		this.id = id;
		this.type = type;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
