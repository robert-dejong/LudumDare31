package com.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.game.Main;
import com.game.build.farm.Farming;
import com.game.damage.Hit;
import com.game.mob.Enemy;
import com.game.sound.Sounds;
import com.game.sprites.SpriteSheet;
import com.game.sprites.Sprites;
import com.game.world.Tile;

public class Input implements KeyListener, MouseListener, MouseWheelListener {
	
	public static boolean[] keysPressed = new boolean[1000];
	
	@Override
	public void keyPressed(KeyEvent k) {
		if(Main.getMain().start) {
			Main.getMain().start = false;
			return;
		}
		
		keysPressed[k.getKeyCode()] = true;
		
		if(Main.getMain().getPlayer().isDead()) {
			if(Input.keysPressed[KeyEvent.VK_SPACE]) {
				Main.getMain().reset();
				return;
			}
		}
		
		if(Input.keysPressed[KeyEvent.VK_ESCAPE] || Input.keysPressed[KeyEvent.VK_P]) {
			Main.getMain().togglePause();
			return;
		}
		
		if(Input.keysPressed[KeyEvent.VK_SPACE]) {
			if(System.currentTimeMillis() - Main.getMain().getPlayer().getLastAttack() < 450)
				return;
			
			int dir = Main.getMain().getPlayer().getDirection();
			int x = Main.getMain().getPlayer().getX();
			int y = Main.getMain().getPlayer().getY();
			int damage = Main.getMain().getPlayer().getDamage();
			
			for(int i = 0; i < Main.getMain().getMob().enemies.size(); i++) {
				Enemy enemy = Main.getMain().getMob().enemies.get(i);
				
				if(enemy == null)
					continue;
				
				if(Main.getMain().getPlayer().withinDistance(enemy.getX(), enemy.getY(), 70)) {
					if((dir == 1 && x > enemy.getX())
							|| (dir == 2 && x < enemy.getX())
							|| (dir == 3 && y > enemy.getY())
							|| (dir == 4 && y < enemy.getY())) {
						Main.getMain().getPlayer().setLastAttack(System.currentTimeMillis());
						Main.getMain().getPlayer().switchAnimation();
						enemy.dealDamage(damage);
						Main.getMain().getSound().playSound(Sounds.HIT);
						return;
					}
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent k) {
		keysPressed[k.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent m) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		
		if(Main.getMain().paused)
			return;
		
		if(m.getButton() == MouseEvent.BUTTON1) {
			if(Main.getMain().getMousePosition() == null)
				return;
			
			double x = Main.getMain().getMousePosition().getX();
			double y = Main.getMain().getMousePosition().getY();
			
			if(x > 971 && x < Main.GAME_WIDTH && y > 689 && y < Main.GAME_HEIGHT) {
				if(!Main.getMain().getTime().isNight())
					Main.getMain().getBuild().toggle();
			}
			
			if(!Main.getMain().getBuild().isBuilding()) {
				if(Main.getMain().getSelected().getSelect() != null) {
					
					if(Main.getMain().getSelected().getSelect().getTile() == Sprites.FARM_5) {
						Tile tile = Main.getMain().getSelected().getSelect();
						Main.getMain().getWorld().objects.remove(tile);
						Main.getMain().getDamage().hits.add(new Hit(tile.getX() + (SpriteSheet.TILE_SIZE), tile.getY(), 10, true));
						Main.getMain().getPlayer().setCash(Main.getMain().getPlayer().getCash() + Farming.WHEAT_CASH);
						Main.getMain().getSound().playSound(Sounds.FARM);
						return;
					}
					
					if(Main.getMain().getSelected().getSelect().getTile() == Sprites.MUD) {
						Tile tile = Main.getMain().getSelected().getSelect();
						Main.getMain().getWorld().objects.remove(tile);
						Main.getMain().getSound().playSound(Sounds.MUD);
						return;
					}
					
					if(Main.getMain().getSelected().getSelect().getTile() == Sprites.DYNAMITE) {
						Tile tile = Main.getMain().getSelected().getSelect();
						Main.getMain().getExplosion().setExplosive(tile);
						return;
					}
					
				}
			}
			
			if(Main.getMain().getBuild().isShowing() && !Main.getMain().getBuild().isBuilding()) {
				if(Main.getMain().getBuild().getSelect() != null) {
					
					if(Main.getMain().getBuild().getSelect().getTiles()[0] == Sprites.IRON_SWORD) {
						if(Main.getMain().getPlayer().getDamage() == 1) {
							int price = Main.getMain().getBuild().getSelect().getPrice();
							if(Main.getMain().getPlayer().getCash() < price) {
								Main.getMain().getBuild().showText("You don't have enough money.");
								Main.getMain().getBuild().reset();
								return;
							}
							
							Main.getMain().getPlayer().setCash(Main.getMain().getPlayer().getCash() - price);
							Main.getMain().getPlayer().setDamage(2);
							Main.getMain().getBuild().reset();
						} else {
							Main.getMain().getBuild().showText("You already have an iron sword.");
							Main.getMain().getBuild().reset();
						}
						return;
					}
					
					Main.getMain().getBuild().setBuilding(true);
					Main.getMain().getBuild().resetText();
					return;
				}
			}
			if(Main.getMain().getBuild().isBuilding()) {
				Main.getMain().getSelected().build();
			}
		} else {
			if(Main.getMain().getBuild().isBuilding()) {
				Main.getMain().getBuild().reset();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent m) {
		if(Main.getMain().paused)
			return;
		
		int amount = m.getWheelRotation();
		
		if(!Main.getMain().getBuild().isBuilding())
			return;
		
		int dir = Main.getMain().getSelected().getDirection();
		
		if(amount < 0) {
			// UP
			dir--;
		} else if(amount > 0) {
			// DOWN
			dir++;
		}
		
		if(dir > 3)
			dir = 0;
		else if(dir < 0)
			dir = 3;
		
		Main.getMain().getSelected().setDirection(dir);
	}

}
