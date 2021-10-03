package com.game.sprites;

public enum Sprites {
	
	GRASS(1, 1),
	MUD(4, 10),
	
	FENCE_1(1, 2),
	FENCE_2(1, 3),
	FENCE_3(1, 4),
	
	IRON_FENCE_1(1, 5),
	IRON_FENCE_2(1, 6),
	IRON_FENCE_3(1, 7),
	
	BRICK_WALL_1(1, 8),
	BRICK_WALL_2(1, 9),
	BRICK_WALL_3(1, 10),
	
	// GUI
	GUI_BUTTON(2, 1),
	GUI_BUTTON_HOVER(2, 2),
	
	// Items
	IRON_SWORD(2, 3),
	DYNAMITE(2, 4),
	DYNAMITE_DETONATED(2, 5),
	
	SELECT(3, 1),
	HIT(3, 2),
	
	// Farming
	FARM_1(4, 1),
	FARM_2(4, 2),
	FARM_3(4, 3),
	FARM_4(4, 4),
	FARM_5(4, 5),
	
	CACTUS_1(4, 6),
	CACTUS_2(4, 7),
	CACTUS_3(4, 8),
	CACTUS_4(4, 9);
	
	private int row, width;
	
	Sprites(final int row, final int width) {
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
