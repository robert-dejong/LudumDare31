package com.game.sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;

public class Sound {
	
	public AudioClip[] clips = new AudioClip[10];
	
	public Sound() {
		load();
	}
	
	public void load() {
		try {
			for(Sounds sound : Sounds.values()) {
				//Applet.newAudioClip(new File(path).toURI().toURL());
				clips[sound.ordinal()] = Applet.newAudioClip(new File("./data/" + sound.getFile() + ".wav").toURI().toURL());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playSound(final Sounds sound) {
		try {
			clips[sound.ordinal()].play();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
