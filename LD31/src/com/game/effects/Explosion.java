package com.game.effects;

import java.awt.Graphics;
import java.util.ArrayList;

import com.game.Main;
import com.game.mob.Enemy;
import com.game.sound.Sounds;
import com.game.sprites.SpriteSheet;
import com.game.sprites.Sprites;
import com.game.task.Task;
import com.game.world.Tile;

public class Explosion {

	public ArrayList<Position> explosions = new ArrayList<Position>();

	public final static int DETONATE_TIME = 3000;
	public final static int EXPLOSION_DURATION = 500;

	public void setExplosive(final Tile tile) {
		if(tile.getState() != 0)
			return;
		
		tile.setState(1);
		tile.setTile(Sprites.DYNAMITE_DETONATED);
		
		Main.getMain().getTask().add(new Task(DETONATE_TIME, false) {

			@Override
			public void execute() {
				setGraphic(tile.getX(), tile.getY());
				Main.getMain().getWorld().objects.remove(tile);
				
				for(int i = 0; i < Main.getMain().getMob().enemies.size(); i++) {
					Enemy enemy = Main.getMain().getMob().enemies.get(i);
					
					if(enemy == null)
						continue;
					
					if(Main.getMain().getPlayer().isColliding(tile.getX() - 96, tile.getY() - 96, 240, 240, enemy.getX(), enemy.getY(), SpriteSheet.MOB_X, SpriteSheet.MOB_Y)) {
						enemy.dealDamage(enemy.getHealth());
						continue;
					}
				}
				
				Main.getMain().getSound().playSound(Sounds.EXPLOSION);
			}

			@Override
			public void stop() {

			}

		});
	}

	public void setGraphic(final int x, final int y) {
		final Position position = new Position(x, y);
		explosions.add(position);

		Main.getMain().getTask().add(new Task(EXPLOSION_DURATION, false) {

			@Override
			public void execute() {
				explosions.remove(position);
			}

			@Override
			public void stop() {

			}

		});
	}

	public void render(Graphics g) {
		if (explosions.size() == 0)
			return;

		for (int i = 0; i < explosions.size(); i++) {
			Position position = explosions.get(i);

			if (position == null)
				continue;

			g.drawImage(Main.getMain().getSprites().getExplosionImage(),
					position.getX() - 96, position.getY() - 96, null);
		}
	}

}
