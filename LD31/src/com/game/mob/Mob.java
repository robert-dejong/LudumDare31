package com.game.mob;

import com.game.Main;
import com.game.sound.Sounds;
import com.game.sprites.Mobs;

public class Mob {
	
	private Mobs animation;
	private int walkingTicks = 0;
	
	private int direction = 0;
	private int x, y;
	
	private int health = 10;
	
	public Mob() {
		
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
		
		if(this.health < 1) {
			if(this instanceof Player) {
				Main.getMain().getPlayer().setDead(true);
				Main.getMain().getSound().playSound(Sounds.DEATH);
				Main.getMain().getMob().enemies.clear();
			}
		}
	}

	public Mobs getAnimation() {
		return animation;
	}

	public void setAnimation(Mobs animation) {
		this.animation = animation;
	}

	public int getWalkingTicks() {
		return walkingTicks;
	}

	public void setWalkingTicks(int walkingTicks) {
		this.walkingTicks = walkingTicks;
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

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

}
