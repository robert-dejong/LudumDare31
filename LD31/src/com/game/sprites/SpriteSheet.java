package com.game.sprites;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	public final static int TILE_SIZE = 48;
	
	public final static int MOB_X = 31, MOB_Y = 42;
	
	public BufferedImage[] tiles = new BufferedImage[30];
	public BufferedImage[] mobs = new BufferedImage[30];
	
	private BufferedImage buildImage;
	private BufferedImage houseImage;
	private BufferedImage night;
	private BufferedImage gameOver;
	private BufferedImage explosion;
	
	public SpriteSheet() {
		loadSprites();
		loadMobs();
	}
	
	public void loadSprites() {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(new File("./data/sprites.png"));
			buildImage = ImageIO.read(new File("./data/build_interface.png"));
			houseImage = ImageIO.read(new File("./data/house.png"));
			night = ImageIO.read(new File("./data/night.png"));
			gameOver = ImageIO.read(new File("./data/game_over.png"));
			explosion = ImageIO.read(new File("./data/explosion.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		for(Sprites tile : Sprites.values()) {
			tiles[tile.ordinal()] = image.getSubimage((tile.getWidth() - 1) * TILE_SIZE, (tile.getRow() - 1) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
		}
	}
	
	public void loadMobs() {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(new File("./data/mobs.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		int SIZE_X = MOB_X;
		int SIZE_Y = MOB_Y;
		
		for(Mobs mob : Mobs.values()) {
			
			if(mob.getRow() == 0)
				continue;
			
			mobs[mob.ordinal()] = image.getSubimage((mob.getWidth() - 1) * SIZE_X, (mob.getRow() - 1) * SIZE_Y, SIZE_X, SIZE_Y);
		}
	}
	
	public BufferedImage getImage(final Sprites tile) {
		try {
			return tiles[tile.ordinal()];
		} catch(Exception e) {
			
		}
		return tiles[Sprites.GRASS.ordinal()];
	}
	
	public BufferedImage getImage(final Mobs mob) {
		return mobs[mob.ordinal()];
	}
	
	public BufferedImage getBuildImage() {
		return buildImage;
	}
	
	public BufferedImage getHouseImage() {
		return houseImage;
	}
	
	public BufferedImage getNightImage() {
		return night;
	}
	
	public BufferedImage getGameOverImage() {
		return gameOver;
	}
	
	public BufferedImage getExplosionImage() {
		return explosion;
	}

}
