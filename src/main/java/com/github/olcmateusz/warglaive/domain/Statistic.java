package com.github.olcmateusz.warglaive.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@JsonIgnoreProperties
public class Statistic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int played;
	private int won;
	private int lost;
	
	public Statistic() {
		
	}
	
	public Statistic(int played, int won, int lost) {
		this.played = played;
		this.won = won;
		this.lost = lost;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPlayed() {
		return played;
	}

	public void setPlayed(int played) {
		this.played = played;
	}

	public int getWon() {
		return won;
	}

	public void setWon(int won) {
		this.won = won;
	}

	public int getLost() {
		return lost;
	}

	public void setLost(int lost) {
		this.lost = lost;
	}

	
	
}
