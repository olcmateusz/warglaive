package com.github.olcmateusz.warglaive.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class LeaderboardsResponse {

	private int id;
	private String name;
	private Bracket bracket;
	private List<Player> entries;
	
	


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Bracket getBracket() {
		return bracket;
	}

	public void setBracket(Bracket bracket) {
		this.bracket = bracket;
	}

	public List<Player> getEntries() {
		return entries;
	}

	public void setEntries(List<Player> entries) {
		this.entries = entries;
	}

	

	
	
}
