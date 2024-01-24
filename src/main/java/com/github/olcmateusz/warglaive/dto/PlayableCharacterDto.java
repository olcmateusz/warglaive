package com.github.olcmateusz.warglaive.dto;

public class PlayableCharacterDto {
	
	private long id;
	private String name;
	private String realmName;
	
	public PlayableCharacterDto() {
		
	}
	
	public PlayableCharacterDto(long id, String name, String realmName) {
		this.id = id;
		this.name = name;
		this.realmName = realmName;
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
	public String getRealmName() {
		return realmName;
	}
	public void setRealmName(String realmName) {
		this.realmName = realmName;
	}

}
