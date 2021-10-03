package com.game.mob;

import java.awt.event.KeyEvent;

import com.game.Main;
import com.game.input.Input;
import com.game.sprites.Mobs;
import com.game.sprites.SpriteSheet;
import com.game.sprites.Sprites;
import com.game.world.Tile;

public class Player extends Mob {
	
	private long lastAttack;
	private boolean isDead = false;
	
	private int damage = 1;
	
	private int cash = 2000;
	
	public Player() {
		setX(505);
		setY(300);
		setAnimation(Mobs.PLAYER_DOWN1);
		setDirection(1);
		setHealth(100);
	}
	
	public void switchAnimation() {
		String dir = getDirection() == 2 ? "RIGHT" : getDirection() == 1 ? "LEFT" : getDirection() == 4 ? "DOWN" : "UP";
		int anim = getWalkingTicks() <= 10 ? 2 : 1;
		setAnimation(Mobs.valueOf("PLAYER_" + dir + anim));
	}
	
	public void tick() {
		int x = getX() + (Input.keysPressed[KeyEvent.VK_LEFT] ? -4 : Input.keysPressed[KeyEvent.VK_RIGHT] ? 4 : 0);
		int y = getY() + (Input.keysPressed[KeyEvent.VK_UP] ? -4 : Input.keysPressed[KeyEvent.VK_DOWN] ? 4 : 0);
		
		if(x != getX() || y != getY()) {
			setWalkingTicks(getWalkingTicks() + 1);
			setDirection(x > getX() ? 2 : x < getX() ? 1 : y > getY() ? 4 : 3);
			String dir = getDirection() == 2 ? "RIGHT" : getDirection() == 1 ? "LEFT" : getDirection() == 4 ? "DOWN" : "UP";
			int anim = getWalkingTicks() <= 10 ? 1 : 2;
			setAnimation(Mobs.valueOf("PLAYER_" + dir + anim));
			
			int SIZE_X = SpriteSheet.MOB_X;
			int SIZE_Y = SpriteSheet.MOB_Y - 20;
			
			if(x < 0 || x + SIZE_X > Main.GAME_WIDTH || y < 0 || y + (SpriteSheet.MOB_Y + 33) > Main.GAME_HEIGHT)
				return;
			
			if(x + SIZE_X > Main.getMain().houseX && x < Main.getMain().houseX + Main.getMain().getSprites().getHouseImage().getWidth()
					&& y + 15 + SIZE_Y > Main.getMain().houseY && y + 15 < Main.getMain().houseY + Main.getMain().getSprites().getHouseImage().getHeight()) {
				return;
			}
			
			int TILE_SIZE = SpriteSheet.TILE_SIZE;
			int tileX, tileY;
			
			for(int i = 0; i < Main.getMain().getWorld().objects.size(); i++) {
				Tile tile = Main.getMain().getWorld().objects.get(i);
				
				if(tile == null)
					continue;
				
				if(tile.isWalk())
					continue;
				
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
					return;
				}
			}
			
			setX(x);
			setY(y);
			
			if(getWalkingTicks() > 20) {
				setWalkingTicks(0);
			}
		}
	}
	
	public boolean isColliding(final int x, final int y, final int width, final int height) {
		int PLAYER_X = SpriteSheet.MOB_X;
		int PLAYER_Y = SpriteSheet.MOB_Y;
		
		return (getX() + PLAYER_X > x && getX() < x + width
				&& getY() + PLAYER_Y > y && getY() < y + height);
	}
	
	public boolean isColliding(final int x1, final int y1, final int width1, final int height1, final int x2, final int y2, final int width2, final int height2) {
		return (x1 + width1 > x2 && x1 < x2 + width2
				&& y1 + height1 > y2 && y1 < y2 + height2);
	}
	
	public boolean withinDistance(final int x, final int y, final int distance) {
		int distanceX = (x > getX() ? x - getX() : getX() - x);
		int distanceY = (y > getY() ? y - getY() : getY() - y);
		
		return (distanceX <= distance && distanceY <= distance);
	}

	public long getLastAttack() {
		return lastAttack;
	}

	public void setLastAttack(long lastAttack) {
		this.lastAttack = lastAttack;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

}
