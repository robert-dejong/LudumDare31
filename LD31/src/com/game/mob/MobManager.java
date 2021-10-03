package com.game.mob;

import java.awt.Graphics;
import java.util.ArrayList;

import com.game.Main;
import com.game.sprites.Mobs;
import com.game.sprites.SpriteSheet;
import com.game.sprites.Sprites;
import com.game.world.Tile;

public class MobManager {
	
	public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	public int getCash(final Mobs mob) {
		switch(mob) {
		case ENEMY1:
			return 10;
			
		case ENEMY2:
			return 15;
			
		default:
			return -1;
		}
	}
	
	private int getHealth(final Mobs mob) {
		switch(mob) {
		case ENEMY1:
			return 3;
			
		case ENEMY2:
			return 4;
			
		default:
			return -1;
		}
	}
	
	private int getDamage(final Mobs mob) {
		switch(mob) {
		case ENEMY1:
			return 1;
			
		case ENEMY2:
			return 2;
			
		default:
			return 1;
		}
	}
	
	private int getSpeed(final Mobs mob) {
		switch(mob) {
		case ENEMY1:
			return 1;
			
		case ENEMY2:
			return 1;
			
		default:
			return 1;
		}
	}
	
	public void tick() {
		if(enemies.size() == 0)
			return;
		
		int SIZE_X = SpriteSheet.MOB_X;
		int SIZE_Y = SpriteSheet.MOB_Y;
		
		boolean night = Main.getMain().getTime().isNight();
		
		for(int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			
			if(enemy == null)
				continue;
			
			boolean walk = true;
			
			int speed = getSpeed(enemy.getMob());
			int dir = enemy.getDirection();
			
			int x = enemy.getX() + (dir == 1 ? speed : dir == 2 ? -speed : 0);
			int y = enemy.getY() + (dir == 3 ? -speed : 0);
			
			enemy.setWalkingTicks(enemy.getWalkingTicks() + 1);
			String direction = dir == 1 ? "RIGHT" : dir == 2 ? "LEFT" : dir == 3 ? "UP" : "DOWN";
			int anim = enemy.getWalkingTicks() <= 10 ? 1 : 2;
			enemy.setAnimation(Mobs.valueOf(enemy.getMob().toString() + "_" + direction + anim));
			
			int TILE_SIZE = SpriteSheet.TILE_SIZE;
			int tileX, tileY;
			
			if(!Main.getMain().getTime().isNight() && !enemy.leaving) {
				enemy.leaving = true;
				enemy.setDirection(dir == 1 ? 2 : dir == 2 ? 1 : (1 + (int)(Math.random() * 1)));
			}
			
			if(!night) {
				if(enemy.getX() < 0 || enemy.getX() > Main.GAME_WIDTH) {
					enemies.remove(enemy);
					continue;
				}
			}
			
			if(x + SIZE_X > Main.getMain().houseX && x < Main.getMain().houseX + Main.getMain().getSprites().getHouseImage().getWidth()
					&& y + 15 + SIZE_Y > Main.getMain().houseY && y + 15 < Main.getMain().houseY + Main.getMain().getSprites().getHouseImage().getHeight()) {
				// Attack the house
				if(System.currentTimeMillis() - enemy.getLastAttack() >= 1000) {
					enemy.setLastAttack(System.currentTimeMillis());
					
					enemy.setWalkingTicks(enemy.getWalkingTicks() + 10);
					Main.getMain().getPlayer().setHealth(Main.getMain().getPlayer().getHealth() - getDamage(enemy.getMob()));
					Main.getMain().getDamage().add(enemy.getX(), enemy.getY() - 48, getDamage(enemy.getMob()));
				}
				continue;
			}
			
			if(!enemy.leaving) {
				if(dir < 3) {
					int random = enemy.getRandom();
					if(x + SIZE_X > Main.getMain().houseX + (dir == 1 ? random : 0) && x < Main.getMain().houseX - (dir == 2 ? random : 0) + Main.getMain().getSprites().getHouseImage().getWidth()) {
						enemy.setDirection(3);
					}
				} else if(dir == 3) {
					if(y < 500) {
						if(x + SIZE_X < Main.getMain().houseX)
							enemy.setDirection(1);
						else if(x > Main.getMain().houseX + Main.getMain().getSprites().getHouseImage().getWidth())
							enemy.setDirection(2);
					}
				}
			}
			
			for(int j = 0; j < Main.getMain().getWorld().objects.size(); j++) {
				Tile tile = Main.getMain().getWorld().objects.get(j);
				
				if(tile == null)
					continue;
				
				if(tile.isWalk()) {
					if(tile.getTile() == Sprites.MUD) {
						if(enemy.getSlowed() < 1 && Main.getMain().getPlayer().isColliding(enemy.getX(), enemy.getY() + 35, SpriteSheet.MOB_X, SpriteSheet.MOB_Y - 35, tile.getX(), tile.getY(), SpriteSheet.TILE_SIZE, SpriteSheet.TILE_SIZE)) {
							enemy.setSlowed(2);
						}
					} else if(tile.getTile() != Sprites.MUD){
						if(Main.getMain().getPlayer().isColliding(enemy.getX(), enemy.getY() + 20, SpriteSheet.MOB_X, SpriteSheet.MOB_Y - 20, tile.getX(), tile.getY(), SpriteSheet.TILE_SIZE, SpriteSheet.TILE_SIZE)) {
							enemy.setSlowed(0);
						}
					}
					continue;
				}
				
				tileX = tile.getX();
				tileY = tile.getY();
				int sizeX = TILE_SIZE;
				int sizeY = TILE_SIZE;
				
				if(tile.getTile() == Sprites.FENCE_3 || tile.getTile() == Sprites.IRON_FENCE_3) {
					tileX += 20;
					sizeX -= 10;
				}
				if(tile.getTile() == Sprites.FENCE_2 || tile.getTile() == Sprites.IRON_FENCE_2) {
					sizeX -= 25;
				}
				
				if(x + SIZE_X > tileX && x < tileX + sizeX
						&& y + 15 + SIZE_Y > tileY && y + 15 < tileY + sizeY) {
					// Attack tile
					walk = false;
					if(System.currentTimeMillis() - enemy.getLastAttack() >= 1000) {
						enemy.setLastAttack(System.currentTimeMillis());
						
						if(tile.getTile() == Sprites.CACTUS_4) { // Cactus
							tile.setHealth(tile.getHealth() - 1);
							enemy.dealDamage(1);
							enemy.setX(enemy.getX() + (enemy.getDirection() == 1 ? -15 : enemy.getDirection() == 2 ? 15 : 0));
							enemy.setY(enemy.getY() + (enemy.getDirection() == 3 ? 15 : 0));
						} else {
							tile.setHealth(tile.getHealth() - getDamage(enemy.getMob()));
							Main.getMain().getDamage().add(tile.getX(), tile.getY(), getDamage(enemy.getMob()));
						}
						
						enemy.setWalkingTicks(enemy.getWalkingTicks() + 10);
						if(tile.getHealth() < 1) {
							Main.getMain().getWorld().objects.remove(tile);
						}
					}
					continue;
				}
			}
			
			if(walk) {
				if(enemy.getSlowed() > 0)
					enemy.setSlowed(enemy.getSlowed() - 1);
				
				if(enemy.getSlowed() < 1) {
					enemy.setX(x);
					enemy.setY(y);
				}
			}
			
			if(enemy.getWalkingTicks() > 20) {
				enemy.setWalkingTicks(0);
			}
		}
	}
	
	public void add(final Mobs mob, final int x, final int y, final int direction, final int health) {
		enemies.add(new Enemy(mob, x, y, direction, health));
	}
	
	public void spawn() {
		int amount = (Main.getMain().getTime().getDay());
		
		for(int i = 0; i < amount; i++) {
			Mobs mob = Mobs.ENEMY1;
			
			if(Main.getMain().getTime().getDay() >= 5 && (Math.random() * 5) < 1)
				mob = Mobs.ENEMY2;
			
			int dir = 1 + (int)(Math.random() * 3);
			
			int x = (int)(336 + (48 * (Math.random() * 8)));
			int y = (int)(100 + (48 * (Math.random() * 6)));
			
			add(mob, dir == 1 ? -20 : dir == 2 ? Main.GAME_WIDTH + 20 : x, dir == 1 ? y : dir == 2 ? y : Main.GAME_HEIGHT + 20, dir, getHealth(mob));
		}
	}
	
	public void render(Graphics g) {
		if(enemies.size() == 0)
			return;
		
		for(int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			
			if(enemy == null)
				continue;
			
			g.drawImage(Main.getMain().getSprites().getImage(enemy.getAnimation()), enemy.getX(), enemy.getY(), null);
		}
	}

}
