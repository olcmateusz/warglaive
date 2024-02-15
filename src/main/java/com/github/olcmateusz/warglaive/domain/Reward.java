package com.github.olcmateusz.warglaive.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Reward {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	@JoinColumn(name="bracket_id")
    public Bracket bracket;
	@ManyToOne
	@JoinColumn(name="achievement_id")
    public Achievement achievement;
    public int rating_cutoff;
    
	public Reward() {
	}
	public Reward(long id, Bracket bracket, Achievement achievement, int rating_cutoff) {
		this.id = id;
		this.bracket = bracket;
		this.achievement = achievement;
		this.rating_cutoff = rating_cutoff;
	}
	public Bracket getBracket() {
		return bracket;
	}
	public void setBracket(Bracket bracket) {
		this.bracket = bracket;
	}
	public Achievement getAchievement() {
		return achievement;
	}
	public void setAchievement(Achievement achievement) {
		this.achievement = achievement;
	}
	public int getRating_cutoff() {
		return rating_cutoff;
	}
	public void setRating_cutoff(int rating_cutoff) {
		this.rating_cutoff = rating_cutoff;
	}
}
