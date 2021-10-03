package com.game.time;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.game.Main;

public class TimeSystem {
	
	private boolean night = false;
	private int day = 0;
	private int seconds = 0;
	private int minutes = 0;
	private int hours = 15;
	private String time = "";
	
	long last = System.currentTimeMillis();
	
	public void tick() {
		if(Main.getMain().getPlayer().isDead())
			return;
		
		if(System.currentTimeMillis() - last >= (isNight() ? 200 : 50)) {
			last = System.currentTimeMillis();
			seconds += 60;
			if(seconds >= 60) {
				seconds = 0;
				minutes++;
			}
			
			if(minutes >= 60) {
				minutes = 0;
				hours++;
				
				if(hours < 7) {
					Main.getMain().getMob().spawn();
				}
			}
			
			if(hours >= 24) {
				hours = 0;
				if(hours == 0) {
					day++;
					Main.getMain().getMob().spawn();
				}
			}
			
			if(hours == 0) {
				Main.getMain().getBuild().reset();
				Main.getMain().getBuild().setShowing(false);
				setNight(true);
			} else if(hours >= 7) {
				setNight(false);
			}
			
			time = (hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		Font font = g.getFont();
		g.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize() + 4));
		g.drawString("Time: " + time, 10, 20);
		g.drawString("Day: " + day, 10, 42);
		g.drawString("Health: " + Main.getMain().getPlayer().getHealth(), 10, 64);
		g.drawString("$" + Main.getMain().getPlayer().getCash(), 10, 86);
		g.setFont(font);
	}

	public boolean isNight() {
		return night;
	}

	public void setNight(boolean night) {
		this.night = night;
	}
	
	public int getDay() {
		return this.day;
	}

}
