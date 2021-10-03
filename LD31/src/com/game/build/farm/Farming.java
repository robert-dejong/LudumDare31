package com.game.build.farm;

import com.game.Main;
import com.game.sprites.SpriteSheet;
import com.game.sprites.Sprites;
import com.game.world.Tile;

public class Farming {
	
	private int ticks = 0;
	
	public final static int MAX_STATE = 90;
	
	public final static int WHEAT_CASH = 20;
	
	public void tick() {
		if(ticks > 0) {
			ticks--;
			return;
		}
		
		ticks = 60;
		for(int i = 0; i < Main.getMain().getWorld().objects.size(); i++) {
			Tile tile = Main.getMain().getWorld().objects.get(i);
			
			if(tile == null)
				continue;
			
			if(tile.getFarmState() == 0)
				continue;
			
			if(tile.getFarmState() >= MAX_STATE)
				continue;
			
			if(tile.getTile() == Sprites.CACTUS_3 && tile.getFarmState() > 85) {
				if(Main.getMain().getPlayer().isColliding(tile.getX(), tile.getY(), SpriteSheet.TILE_SIZE, SpriteSheet.TILE_SIZE)) {
					continue;
				}
			}
			tile.setFarmState(tile.getFarmState() + 1);
			
			if(isWheat(tile.getTile())) { // Wheat farm
				if(tile.getFarmState() >= 90)
					tile.setTile(Sprites.FARM_5);
				else if(tile.getFarmState() >= 70)
					tile.setTile(Sprites.FARM_4);
				else if(tile.getFarmState() >= 50)
					tile.setTile(Sprites.FARM_3);
				else if(tile.getFarmState() >= 30)
					tile.setTile(Sprites.FARM_2);
			} else {
				// Cactus
				if(tile.getFarmState() >= 90) {
					tile.setWalk(false);
					tile.setTile(Sprites.CACTUS_4);
				} else if(tile.getFarmState() >= 60)
					tile.setTile(Sprites.CACTUS_3);
				else if(tile.getFarmState() >= 30)
					tile.setTile(Sprites.CACTUS_2);
			}
			
		}
	}
	
	public boolean isWheat(final Sprites tile) {
		return (tile == Sprites.FARM_1 || tile == Sprites.FARM_2 || tile == Sprites.FARM_3 || tile == Sprites.FARM_4 || tile == Sprites.FARM_5);
	}

}
