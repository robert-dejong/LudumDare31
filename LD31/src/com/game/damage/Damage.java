package com.game.damage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import com.game.Main;
import com.game.sprites.Sprites;

public class Damage {
	
	public ArrayList<Hit> hits = new ArrayList<Hit>();
	
	private int ticks = 0;
	
	public void add(final int x, final int y, final int amount) {
		hits.add(new Hit(x, y, amount));
	}
	
	public void tick() {
		if(hits.size() == 0)
			return;
		
		if(ticks > 0) {
			ticks--;
			return;
		}
		
		ticks = 2;
		
		for(int i = 0; i < hits.size(); i++) {
			Hit hit = hits.get(i);
			
			if(hit == null)
				continue;
			
			hit.setDuration(hit.getDuration() + 1);
			
			if(hit.getDuration() > 15) {
				hits.remove(hit);
				continue;
			}
		}
	}
	
	public void render(Graphics g) {
		if(hits.size() == 0)
			return;
		
		Font font = g.getFont();
		g.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize() + 2));
		
		for(int i = 0; i < hits.size(); i++) {
			Hit hit = hits.get(i);
			
			if(hit == null)
				continue;
			
			if(hit.getCash() > 0) {
				g.setColor(Color.GREEN);
				g.drawString("+$" + hit.getCash(), hit.getX(), hit.getY() - (hit.getDuration() * 4));
				continue;
			}
			
			g.setColor(Color.RED);
			
			if(hit.getDuration() < 5)
				g.drawImage(Main.getMain().getSprites().getImage(Sprites.HIT), hit.getX(), hit.getY(), null);
			
			g.drawString("-" + hit.getAmount(), hit.getX(), hit.getY() - (hit.getDuration() * 4));
			continue;
		}
		
		g.setFont(font);
	}

}
