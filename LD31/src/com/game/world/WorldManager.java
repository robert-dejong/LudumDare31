package com.game.world;

import java.util.ArrayList;

import com.game.Main;
import com.game.sprites.SpriteSheet;
import com.game.sprites.Sprites;

public class WorldManager {
	
	public ArrayList<Tile> tiles = new ArrayList<Tile>();
	public ArrayList<Tile> objects = new ArrayList<Tile>();
	
	public WorldManager() {
		
	}
	
	public void load() {
		if(tiles.size() > 0)
			tiles.clear();
		
		int SIZE = SpriteSheet.TILE_SIZE;
		
		int x = 0;
		int y = 0;
		for(int i = 0; i < 352; i++) {
			tiles.add(new Tile(Sprites.GRASS, x * SIZE, y * SIZE));
			
			x++;
			if((x * SIZE) > Main.GAME_WIDTH) {
				x = 0;
				y++;
			}
		}
	}

}
