package com.game.mob;

import com.game.Main;
import com.game.damage.Hit;
import com.game.sprites.Mobs;

public class Enemy extends Mob {
	
	private Mobs mob;
	
	private long lastAttack;
	private int random;
	
	private int slowed = 0;
	
	public boolean leaving = false;
	
	public Enemy(final Mobs mob, final int x, final int y, final int direction, final int health) {
		setRandom(20 + (int)(Math.random() * 150));
		setMob(mob);
		setHealth(health);
		setAnimation(mob);
		setX(x);
		setY(y);
		setDirection(direction);
	}
	
	public void dealDamage(final int amount) {
		setHealth(getHealth() - amount);
		Main.getMain().getDamage().add(getX(), getY(), amount);
		
		if(getHealth() < 1) {
			int gold = Main.getMain().getMob().getCash(getMob());
			Main.getMain().getDamage().hits.add(new Hit(getX() + 17, getY(), gold, true));
			Main.getMain().getPlayer().setCash(Main.getMain().getPlayer().getCash() + gold);
			Main.getMain().getMob().enemies.remove(this);
			return;
		}
	}

	public Mobs getMob() {
		return mob;
	}

	public void setMob(Mobs mob) {
		this.mob = mob;
	}

	public long getLastAttack() {
		return lastAttack;
	}

	public void setLastAttack(long lastAttack) {
		this.lastAttack = lastAttack;
	}

	public int getRandom() {
		return random;
	}

	public void setRandom(int random) {
		this.random = random;
	}
	
	public int getSlowed() {
		return slowed;
	}

	public void setSlowed(int slowed) {
		this.slowed = slowed;
	}

}
