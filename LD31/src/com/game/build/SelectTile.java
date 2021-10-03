package com.game.build;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.game.Main;
import com.game.sprites.SpriteSheet;
import com.game.sprites.Sprites;
import com.game.world.Tile;

public class SelectTile {
	
	private Tile select;
	private int direction = 0;
	
	public void tick() {
		Point mousePosition = Main.getMain().getMousePosition();
		
		if(mousePosition == null)
			return;
		
		if(!Main.getMain().getBuild().isBuilding()) {
			int SIZE = SpriteSheet.TILE_SIZE;
			int x = 0, y = 0;
			try {
				x = (int)mousePosition.getX();
				y = (int)mousePosition.getY();
			} catch(Exception e) {
				return;
			}
			
			for(int i = 0; i < Main.getMain().getWorld().objects.size(); i++) {
				Tile tile = Main.getMain().getWorld().objects.get(i);
				
				if(tile == null)
					continue;
				
				if(x > tile.getX() && x < tile.getX() + SIZE && y > tile.getY() && y < tile.getY() + SIZE) {
					setSelect(tile);
					return;
				}
			}
			
			setSelect(null);
			return;
		}
		
		if(!Main.getMain().getBuild().isShowing())
			return;
		
		int SIZE = SpriteSheet.TILE_SIZE;
		int x = (int)mousePosition.getX();
		int y = (int)mousePosition.getY();
		
		for(int i = 0; i < Main.getMain().getWorld().tiles.size(); i++) {
			Tile tile = Main.getMain().getWorld().tiles.get(i);
			
			if(tile == null)
				return;
			
			if(x > tile.getX() && x < tile.getX() + SIZE && y > tile.getY() && y < tile.getY() + SIZE) {
				setSelect(tile);
				return;
			}
		}
		
		setSelect(null);
	}
	
	public void render(Graphics g) {
		
		if(!Main.getMain().getBuild().isBuilding()) {
			
			if(getSelect() != null)
				g.drawImage(Main.getMain().getSprites().getImage(Sprites.SELECT), getSelect().getX(), getSelect().getY(), null);
			
			return;
		}
		
		if(!Main.getMain().getBuild().isShowing())
			return;
		
		if(Main.getMain().getBuild().getSelect() == null)
			return;
		
		if(getSelect() == null)
			return;
		
		if(getSelect().getX() > 760 && getSelect().getY() > 460) {
			return;
		}
		
		if(getSelect().getX() > 309 && getSelect().getY() >= 0
				&& getSelect().getX() < 720 && getSelect().getY() < 209)
			return;
		
		if(getSelect().getX() + 48 > Main.getMain().getPlayer().getX() && getSelect().getX() < Main.getMain().getPlayer().getX() + SpriteSheet.MOB_X
				&& getSelect().getY() + 48 > Main.getMain().getPlayer().getY() && getSelect().getY() < Main.getMain().getPlayer().getY() + SpriteSheet.MOB_Y) {
			return;
		}
		
		BufferedImage image = Main.getMain().getSprites().getImage(Main.getMain().getBuild().getSelect().getTiles()[getDirection()]);
		g.drawImage(image, getSelect().getX(), getSelect().getY(), null);
		g.drawImage(Main.getMain().getSprites().getImage(Sprites.SELECT), getSelect().getX(), getSelect().getY(), null);
	}
	
	public void build() {
		if(getSelect() == null) {
			Main.getMain().getBuild().reset();
			return;
		}
		if(getSelect().getX() > 760 && getSelect().getY() > 460) {
			Main.getMain().getBuild().reset();
			return;
		}
		
		if(getSelect().getX() > 309 && getSelect().getY() >= 0
				&& getSelect().getX() < 720 && getSelect().getY() < 209) {
			Main.getMain().getBuild().reset();
			return;
		}
		
		if(getSelect().getX() + 48 > Main.getMain().getPlayer().getX() && getSelect().getX() < Main.getMain().getPlayer().getX() + SpriteSheet.MOB_X
				&& getSelect().getY() + 48 > Main.getMain().getPlayer().getY() && getSelect().getY() < Main.getMain().getPlayer().getY() + SpriteSheet.MOB_Y) {
			Main.getMain().getBuild().reset();
			return;
		}
		
		for(int i = 0; i < Main.getMain().getWorld().objects.size(); i++) {
			Tile tile = Main.getMain().getWorld().objects.get(i);
			
			if(tile == null)
				continue;
			
			if(getSelect().getX() == tile.getX() && getSelect().getY() == tile.getY()) {
				Main.getMain().getBuild().reset();
				return;
			}
		}
		
		int price = Main.getMain().getBuild().getSelect().getPrice();
		if(Main.getMain().getPlayer().getCash() < price) {
			Main.getMain().getBuild().showText("You don't have enough money to make this.");
			Main.getMain().getBuild().reset();
			return;
		}
		
		Sprites build = Main.getMain().getBuild().getSelect().getTiles()[0];
		
		Main.getMain().getPlayer().setCash(Main.getMain().getPlayer().getCash() - price);
		
		Tile buildTile = new Tile(Main.getMain().getBuild().getSelect().getTiles()[getDirection()], getSelect().getX(), getSelect().getY(), false);
		Main.getMain().getWorld().objects.add(buildTile);
		
		if(build == Sprites.FARM_1) {
			buildTile.setFarmState(1);
			buildTile.setWalk(true);
		} else if(build == Sprites.CACTUS_1) {
			buildTile.setFarmState(1);
			buildTile.setWalk(true);
			buildTile.setHealth(Main.getMain().getBuild().getSelect().getHealth());
		} else if(build == Sprites.MUD) {
			buildTile.setWalk(true);
		} else if(build == Sprites.DYNAMITE) {
			buildTile.setWalk(true);
		} else {
			buildTile.setHealth(Main.getMain().getBuild().getSelect().getHealth());
		}
		
		Main.getMain().getBuild().reset();
	}

	public Tile getSelect() {
		return select;
	}

	public void setSelect(Tile select) {
		this.select = select;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

}
