package com.game.sound;

public enum Sounds {
	
	HIT("hit"),
	FARM("farm"),
	EXPLOSION("explosion"),
	MUD("mud"),
	DEATH("death");
	
	private String file;
	
	Sounds(final String file) {
		this.file = file;
	}
	
	public String getFile() {
		return file;
	}

}
