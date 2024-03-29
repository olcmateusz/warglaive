package com.github.olcmateusz.warglaive.domain;

import com.github.olcmateusz.warglaive.enums.Faction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Player {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	@JoinColumn(name="character_id")
	private PlayableCharacter character;
	@Enumerated(EnumType.STRING)
	private Faction faction;
	@Column(name = "`rank`")
	private int rank;
	private int rating;
	private String bracket;
	@ManyToOne
	@JoinColumn(name="bracket_id")
	private Bracket bracketFull;
	private String region;
	@ManyToOne
	@JoinColumn(name="season_match_statistics_id")
	private Statistic season_match_statistics;
	
	public Player() {
		
	}
	
	public Player(PlayableCharacter character, Faction faction, int rank, int rating,
			Statistic season_match_statistics) {
		this.character = character;
		this.faction = faction;
		this.rank = rank;
		this.rating = rating;
		this.season_match_statistics = season_match_statistics;
	}
	
	

	public Player(long id, PlayableCharacter character, Faction faction, int rank, int rating, String bracket,
			String region, Statistic season_match_statistics) {
		this.id = id;
		this.character = character;
		this.faction = faction;
		this.rank = rank;
		this.rating = rating;
		this.bracket = bracket;
		this.region = region;
		this.season_match_statistics = season_match_statistics;
	}

	public Player(PlayableCharacter character, Faction faction, int rank, int rating, String bracket, Bracket bracketFull, String region,
			Statistic season_match_statistics) {
		this.character = character;
		this.faction = faction;
		this.rank = rank;
		this.rating = rating;
		this.bracket = bracket;
		this.bracketFull = bracketFull;
		this.region = region;
		this.season_match_statistics = season_match_statistics;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PlayableCharacter getCharacter() {
		return character;
	}

	public void setCharacter(PlayableCharacter character) {
		this.character = character;
	}

	public Faction getFaction() {
		return faction;
	}

	public void setFaction(Faction faction) {
		this.faction = faction;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Statistic getSeason_match_statistics() {
		return season_match_statistics;
	}

	public void setSeason_match_statistics(Statistic season_match_statistics) {
		this.season_match_statistics = season_match_statistics;
	}
	
	

	public String getBracket() {
		return bracket;
	}

	public void setBracket(String bracket) {
		this.bracket = bracket;
	}

	public Bracket getBracketFull() {
		return bracketFull;
	}

	public void setBracketFull(Bracket bracketFull) {
		this.bracketFull = bracketFull;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", character=" + character + ", faction=" + faction + ", rank=" + rank + ", rating="
				+ rating + ", bracket=" + bracket + ", region=" + region + ", season_match_statistics="
				+ season_match_statistics + "]";
	}

	
	
	

}
