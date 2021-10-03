package com.game.build;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import com.game.Main;
import com.game.sprites.Sprites;

public class BuildManager {
	
	private boolean isShowing = false;
	
	public int ticks = 0;
	
	public int textTicks = 0;
	private boolean showText = false;
	private String text = "";
	
	// 778, 491
	
	public void tick() {
		if(ticks > 0) {
			ticks--;
			return;
		}
		
		if(isShowText()) {
			textTicks--;
			if(textTicks < 1) {
				resetText();
			}
		}
		
		if(!isShowing())
			return;
		
		ticks = 3;
		setSelected();
		
	}
	
	public void showText(final String text) {
		textTicks = 40;
		setShowText(true);
		setText(text);
	}
	
	public void resetText() {
		textTicks = 0;
		setShowText(false);
		setText("");
	}
	
	private Build[] builds = { Build.WOODEN_FENCE, Build.IRON_FENCE, Build.BRICK_WALL, null,
			Build.WHEAT_FARM, Build.CACTUS, null, null,
			Build.MUD, null, null, null,
			Build.IRON_SWORD, null, null, null,
			Build.DYNAMITE};
	
	private int selectX, selectY;
	private Build select;
	
	private boolean isBuilding = false;
	
	public void render(Graphics g) {
		int x = 0;
		int y = 0;
		for(int i = 0; i < builds.length; i++) {
			Build build = builds[i];
			
			if(build == null) {
				x++;
				if(x > 3) {
					x = 0;
					y++;
				}
				continue;
			}
			
			g.drawImage(Main.getMain().getSprites().getImage(build.getTiles()[0]), 775 + (48 * x), 488 + (48 * y), 32, 32, null);
			
			x++;
			if(x > 3) {
				x = 0;
				y++;
			}
		}
		
		if(isBuilding())
			return;
		
		if(getSelect() != null) {
			g.setColor(Color.WHITE);
			Font font = g.getFont();
			
			g.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize()));
			g.drawString(getSelect().getName(), getSelectX() + 10, getSelectY() - 40);
			g.drawString("Price: " + getSelect().getPrice(), getSelectX() + 10, getSelectY() - 23);
			
			if(getSelect().getTiles()[0] == Sprites.CACTUS_1) {
				g.drawString("Once grown, damages", getSelectX() + 10, getSelectY() - 6);
				g.drawString("enemy for " + getSelect().getHealth() + " damage", getSelectX() + 10, getSelectY() + 11);
			} else if(getSelect().getTiles()[0] == Sprites.MUD) {
				g.drawString("Slows opponent by", getSelectX() + 10, getSelectY() - 6);
				g.drawString("50% when walked on", getSelectX() + 10, getSelectY() + 11);
				g.drawString("(Can be removed by", getSelectX() + 10, getSelectY() + 28);
				g.drawString("Clicking it)", getSelectX() + 10, getSelectY() + 45);
			} else if(getSelect().getTiles()[0] == Sprites.DYNAMITE) {
				g.drawString("Explodes 3 seconds", getSelectX() + 10, getSelectY() - 6);
				g.drawString("after clicking on", getSelectX() + 10, getSelectY() + 11);
				g.drawString("and instantly kills", getSelectX() + 10, getSelectY() + 28);
				g.drawString("enemies in a 3 tile range", getSelectX() + 10, getSelectY() + 45);
			}
			
			if(getSelect().getHealth() > 0 && getSelect().getTiles()[0] != Sprites.CACTUS_1)
				g.drawString("Health: " + getSelect().getHealth(), getSelectX() + 10, getSelectY() - 6);
			
			if(getSelect().getTiles()[0] == Sprites.IRON_SWORD)
				g.drawString("Damage: 2", getSelectX() + 10, getSelectY() - 6);
			
			g.setFont(font);
		}
		
		if(isShowText()) {
			Font font = g.getFont();
			g.setColor(Color.WHITE);
			g.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize() + 2));
			g.drawString(getText(), (Main.GAME_WIDTH / 2) - (getText().length() * 5), Main.GAME_HEIGHT - 100);
			
			g.setFont(font);
		}
	}
	
	public void setSelected() {
		Point mousePosition = Main.getMain().getMousePosition();
		
		if(mousePosition == null)
			return;
		
		if(isBuilding())
			return;
		
		int mouseX;
		int mouseY;
		
		try {
			mouseX = (int)mousePosition.getX();
			mouseY = (int)mousePosition.getY();
		} catch(Exception e) {
			return;
		}
		
		int x = 0;
		int y = 0;
		
		
		for(int i = 0; i < builds.length; i++) {
			Build build = builds[i];
			
			if(build == null) {
				x++;
				if(x > 3) {
					x = 0;
					y++;
				}
				continue;
			}
			
			int x2 = 775 + (48 * x);
			int y2 = 488 + (48 * y);
			
			if(mouseX > x2 && mouseX < x2 + 32 && mouseY > y2 && mouseY < y2 + 32) {
				setSelect(build);
				setSelectX(x2);
				setSelectY(y2);
				return;
			}
			
			x++;
			if(x > 3) {
				x = 0;
				y++;
			}
		}
		
		if(!isBuilding())
			setSelect(null);
	}

	public boolean isShowing() {
		return isShowing;
	}

	public void setShowing(boolean isShowing) {
		this.isShowing = isShowing;
	}
	
	public void toggle() {
		this.isShowing = !isShowing;
		reset();
	}
	
	public void reset() {
		setSelect(null);
		setBuilding(false);
		Main.getMain().getSelected().setSelect(null);
		Main.getMain().getSelected().setDirection(0);
	}

	public Build getSelect() {
		return select;
	}

	public void setSelect(Build select) {
		this.select = select;
	}

	public int getSelectX() {
		return selectX;
	}

	public void setSelectX(int selectX) {
		this.selectX = selectX;
	}

	public int getSelectY() {
		return selectY;
	}

	public void setSelectY(int selectY) {
		this.selectY = selectY;
	}

	public boolean isBuilding() {
		return isBuilding;
	}

	public void setBuilding(boolean isBuilding) {
		this.isBuilding = isBuilding;
	}

	public boolean isShowText() {
		return showText;
	}

	public void setShowText(boolean showText) {
		this.showText = showText;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
