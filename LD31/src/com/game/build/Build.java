package com.game.build;

import com.game.sprites.Sprites;

public enum Build {
	
	WOODEN_FENCE("Wooden fence", 20, 5, new Sprites[] { Sprites.FENCE_1, Sprites.FENCE_2, Sprites.FENCE_1, Sprites.FENCE_3 }),
	IRON_FENCE("Iron fence", 50, 15, new Sprites[] { Sprites.IRON_FENCE_1, Sprites.IRON_FENCE_2, Sprites.IRON_FENCE_1, Sprites.IRON_FENCE_3 }),
	BRICK_WALL("Brick wall", 100, 35, new Sprites[] { Sprites.BRICK_WALL_1, Sprites.BRICK_WALL_2, Sprites.BRICK_WALL_1, Sprites.BRICK_WALL_3 }),
	
	WHEAT_FARM("Wheat farm", 10, -1, new Sprites[] { Sprites.FARM_1, Sprites.FARM_1, Sprites.FARM_1, Sprites.FARM_1 }),
	CACTUS("Cactus", 120, 10, new Sprites[] { Sprites.CACTUS_1, Sprites.CACTUS_1, Sprites.CACTUS_1, Sprites.CACTUS_1 }),
	
	MUD("Mud", 25, -1, new Sprites[] { Sprites.MUD, Sprites.MUD, Sprites.MUD, Sprites.MUD }),
	
	IRON_SWORD("Iron sword", 100, -1, new Sprites[] { Sprites.IRON_SWORD, Sprites.IRON_SWORD, Sprites.IRON_SWORD, Sprites.IRON_SWORD, }),
	
	DYNAMITE("Dynamite", 200, -1, new Sprites[] { Sprites.DYNAMITE, Sprites.DYNAMITE, Sprites.DYNAMITE, Sprites.DYNAMITE });
	
	private String name;
	private int price, health;
	private Sprites[] tiles;
	
	Build(final String name, final int price, final int health, final Sprites[] tiles) {
		this.name = name;
		this.price = price;
		this.health = health;
		this.tiles = tiles;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getHealth() {
		return health;
	}
	
	public Sprites[] getTiles() {
		return tiles;
	}

}
