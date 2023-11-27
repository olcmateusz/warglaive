package com.github.olcmateusz.warglaive.domain.rewards;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Reward {
	
    private Bracket bracket;
//    private Achievement achievement;
    private int rating_cutoff;
    
    
	public Bracket getBracket() {
		return bracket;
	}
	public void setBracket(Bracket bracket) {
		this.bracket = bracket;
	}
//	public Achievement getAchievement() {
//		return achievement;
//	}
//	public void setAchievement(Achievement achievement) {
//		this.achievement = achievement;
//	}
	public int getRating_cutoff() {
		return rating_cutoff;
	}
	public void setRating_cutoff(int rating_cutoff) {
		this.rating_cutoff = rating_cutoff;
	}

    
}