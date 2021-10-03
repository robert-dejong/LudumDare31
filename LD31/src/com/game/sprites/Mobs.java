package com.game.sprites;

public enum Mobs {
	
	PLAYER_LEFT1(1, 1),
	PLAYER_LEFT2(1, 2),
	PLAYER_UP1(1, 3),
	PLAYER_UP2(1, 4),
	PLAYER_RIGHT1(1, 5),
	PLAYER_RIGHT2(1, 6),
	PLAYER_DOWN1(1, 7),
	PLAYER_DOWN2(1, 8),
	
	// Enemies
	ENEMY1(2, 1),
	ENEMY1_LEFT1(2, 1),
	ENEMY1_LEFT2(2, 2),
	ENEMY1_UP1(2, 3),
	ENEMY1_UP2(2, 4),
	ENEMY1_RIGHT1(2, 5),
	ENEMY1_RIGHT2(2, 6),
	ENEMY1_DOWN1(2, 7),
	ENEMY1_DOWN2(2, 8),
	
	ENEMY2(3, 1),
	ENEMY2_LEFT1(3, 1),
	ENEMY2_LEFT2(3, 2),
	ENEMY2_UP1(3, 3),
	ENEMY2_UP2(3, 4),
	ENEMY2_RIGHT1(3, 5),
	ENEMY2_RIGHT2(3, 6),
	ENEMY2_DOWN1(3, 7),
	ENEMY2_DOWN2(3, 8);
	
	private int row, width;
	
	Mobs(final int row, final int width) {
		this.row = row;
		this.width = width;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getWidth() {
		return width;
	}

}
