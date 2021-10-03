package com.game.world;

import com.game.sprites.Sprites;

public class Tile {
	
	private Sprites tile;
	private int x, y, farmState = 0, state = 0, health = 0;
	private boolean walk = true;
	
	public Tile(final Sprites tile, final int x, final int y) {
		setTile(tile);
		setX(x);
		setY(y);
	}
	
	public Tile(final Sprites tile, final int x, final int y, final boolean walk) {
		setTile(tile);
		setX(x);
		setY(y);
		setWalk(walk);
	}
	
	public Tile(final Sprites tile, final int x, final int y, final boolean walk, final int health) {
		setTile(tile);
		setX(x);
		setY(y);
		setWalk(walk);
		setHealth(health);
	}

	public Sprites getTile() {
		return tile;
	}

	public void setTile(Sprites tile) {
		this.tile = tile;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isWalk() {
		return walk;
	}

	public void setWalk(boolean walk) {
		this.walk = walk;
	}

	public int getFarmState() {
		return farmState;
	}

	public void setFarmState(int state) {
		this.farmState = state;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
