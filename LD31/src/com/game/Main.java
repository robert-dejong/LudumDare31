package com.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.game.build.BuildManager;
import com.game.build.SelectTile;
import com.game.build.farm.Farming;
import com.game.damage.Damage;
import com.game.effects.Explosion;
import com.game.input.Input;
import com.game.mob.MobManager;
import com.game.mob.Player;
import com.game.sound.Sound;
import com.game.sprites.SpriteSheet;
import com.game.sprites.Sprites;
import com.game.task.TaskManager;
import com.game.time.TimeSystem;
import com.game.world.Tile;
import com.game.world.WorldManager;

public class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	
	public final static String GAME_NAME = "Apocalypse Survival";
	public final static double GAME_VERSION = 0.3;
	public final static int GAME_WIDTH = 1024, GAME_HEIGHT = 768;
	
	public boolean start = true;
	public boolean paused = false;
	
	private boolean hoverBuild = false;
	
	// Constructors
	private static Main main;
	private SpriteSheet sprites = new SpriteSheet();
	private WorldManager world = new WorldManager();
	private Player player = new Player();
	private BuildManager build = new BuildManager();
	private SelectTile select = new SelectTile();
	private TimeSystem time = new TimeSystem();
	private Farming farm = new Farming();
	private MobManager mob = new MobManager();
	private Damage damage = new Damage();
	private Sound sound = new Sound();
	private TaskManager task = new TaskManager();
	private Explosion explosion = new Explosion();
	
	public Main() {
		JFrame frame = new JFrame(GAME_NAME + " - v" + GAME_VERSION);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(GAME_WIDTH, GAME_HEIGHT);
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);
		
		Input input = new Input();
		addKeyListener(input);
		addMouseListener(input);
		addMouseWheelListener(input);
		
		frame.setVisible(true);
		
		frame.add(this);
		frame.revalidate();
		
		new Thread(this).start();
	}
	
	public static void main(String[] args) {
		main = new Main();
	}
	
	public void load() {
		world.load();
	}
	
	public void reset() {
		player = new Player();
		build = new BuildManager();
		select = new SelectTile();
		time = new TimeSystem();
		
		mob.enemies.clear();
		getWorld().objects.clear();
		getDamage().hits.clear();
		
		load();
		
		System.gc();
	}
	
	public void togglePause() {
		this.paused = !paused;
	}
	
	public void tick() {
		if(start || paused)
			return;
		
		getBuild().tick();
		getSelected().tick();
		getTime().tick();
		getPlayer().tick();
		getFarm().tick();
		getMob().tick();
		getDamage().tick();
		getTask().tick();
		
		Point mouse = getMousePosition();
		
		if(mouse != null) {
			if(mouse.getX() > 971 && mouse.getX() < Main.GAME_WIDTH && mouse.getY() > 689 && mouse.getY() < Main.GAME_HEIGHT) {
				hoverBuild = true;
			} else {
				hoverBuild = false;
			}
		} else {
			hoverBuild = false;
		}
	}
	
	public int houseX = (GAME_WIDTH / 2) - (getSprites().getHouseImage().getWidth() / 2 - 15);
	public int houseY = (GAME_HEIGHT / 3) - (getSprites().getHouseImage().getHeight() + 30);
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		if(getPlayer().isDead()) {
			Font font = g.getFont();
			g.setColor(Color.WHITE);
			g.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize() + 4));
			g.drawImage(getSprites().getGameOverImage(), 0, 0, null);
			g.drawString("You have survived " + getTime().getDay() + " day" + (getTime().getDay() != 1 ? "s" : ""), 410, 430);
			g.setFont(font);
			g.dispose();
			bs.show();
			return;
		}
		
		for(int i = 0; i < getWorld().tiles.size(); i++) {
			Tile tile = getWorld().tiles.get(i);
			
			if(tile == null)
				continue;
			
			g.drawImage(getSprites().getImage(tile.getTile()), tile.getX(), tile.getY(), null);
		}
		
		for(int i = 0; i < getWorld().objects.size(); i++) {
			Tile tile = getWorld().objects.get(i);
			
			if(tile == null)
				continue;
			
			g.drawImage(getSprites().getImage(tile.getTile()), tile.getX(), tile.getY(), null);
		}
		
		g.drawImage(getSprites().getHouseImage(), houseX, houseY, null);
		
		getMob().render(g);
		
		g.drawImage(getSprites().getImage(getPlayer().getAnimation()), getPlayer().getX(), getPlayer().getY(), null);
		
		if(getTime().isNight()) {
			g.drawImage(getSprites().getNightImage(), 0, 0, null);
		}
		
		getExplosion().render(g);
		getDamage().render(g);
		
		if(!getTime().isNight()) {
			g.drawImage(getSprites().getImage((hoverBuild ? Sprites.GUI_BUTTON_HOVER : Sprites.GUI_BUTTON)), GAME_WIDTH - 54, GAME_HEIGHT - 83, null);
		}
		
		if(getBuild().isShowing()) {
			g.drawImage(getSprites().getBuildImage(), 754, 467, null);
			getBuild().render(g);
		}
		
		getSelected().render(g);
		
		getTime().render(g);
		
		if(start) {
			g.drawImage(getSprites().getNightImage(), 0, 0, null);
			Font font = g.getFont();
			g.setColor(Color.WHITE);
			g.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize() + 4));
			
			g.drawString("The nations power grid has been compromised by hackers,", 230, 155);
			g.drawString("causing total chaos. Shops are completely empty causing", 230, 180);
			g.drawString("millions of people are starving. As you are self sufficient", 230, 205);
			g.drawString("you don't rely on shops. Unfortunately people know about this", 230, 230);
			g.drawString("And try to break into your house to steal your food.", 230, 255);
			
			g.drawString("Goal: Protect your house as long as possible by building obstacles,", 230, 305);
			g.drawString("killing enemies, and farming. Enemies spawn every hour between 12 PM and 7 AM.", 230, 330);
			g.drawString("Every night more enemies will try to destroy your house, so you will need to", 230, 355);
			g.drawString("Place barricades to prevent them from reaching your house. ", 230, 380);
			g.drawString("Kill them by getting close to them and pressing [SPACE].", 230, 405);
			
			// Controls
			g.drawString("Controls:", 230, 455);
			g.drawString("Escape or P: Pause", 230, 480);
			g.drawString("[SPACE]: Attack", 230, 505);
			g.drawString("Click the button location at the right-bottom of the screen to open/close the building interface", 230, 530);
			g.drawString("Click an object to build and place it somewhere on the map.", 230, 555);
			g.drawString("Use your mouse wheel to rotate the object.", 230, 580);
			
			g.drawString("Press any key to start.", 230, 630);
			
			g.setFont(font);
			g.dispose();
			bs.show();
			return;
		}
		
		if(paused) {
			g.drawImage(getSprites().getNightImage(), 0, 0, null);
			Font font = g.getFont();
			g.setColor(Color.WHITE);
			g.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize() + 4));
			
			String message = "Game Paused - Press Escape or P to continue";
			g.drawString(message, (Main.GAME_WIDTH / 2) - (message.length() * 4), (GAME_HEIGHT / 2) - 20);
			
			g.setFont(font);
		}
		
		g.dispose();
		bs.show();
	}

	@Override
	public void run() {
		double delta = 0;
		double nsPerTick = 1000000000.0 / 60;
		long last = System.nanoTime();
		
		load();
		
		while(true) {
			long now = System.nanoTime();
			delta += (now - last) / nsPerTick;
			last = now;
			
			while(delta >= 1) {
				delta -= 1;
				tick();
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			render();
		}
	}
	
	public static Main getMain() {
		return main;
	}
	
	public SpriteSheet getSprites() {
		return sprites;
	}
	
	public WorldManager getWorld() {
		return world;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public BuildManager getBuild() {
		return build;
	}
	
	public SelectTile getSelected() {
		return select;
	}
	
	public TimeSystem getTime() {
		return time;
	}
	
	public Farming getFarm() {
		return farm;
	}
	
	public MobManager getMob() {
		return mob;
	}
	
	public Damage getDamage() {
		return damage;
	}
	
	public Sound getSound() {
		return sound;
	}
	
	public TaskManager getTask() {
		return task;
	}
	
	public Explosion getExplosion() {
		return explosion;
	}

}
