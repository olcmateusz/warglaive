package com.github.olcmateusz.warglaive.domain.rewards;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Key {
    private String href;

    
    
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

    
}