package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() {
		
		soundURL[0] = getClass().getResource("/sound/GameSong.wav");
		soundURL[1] = getClass().getResource("/sound/coin.wav");
		soundURL[2] = getClass().getResource("/sound/fanfare.wav");
		soundURL[3] = getClass().getResource("/sound/powerup.wav");
		soundURL[4] = getClass().getResource("/sound/unlock.wav");
	}
	
	public void setFile(int i) {
		
		try {
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
			//VOLUME
			 if (i == 0) { // GameSong.wav
		            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		            // Volume is in decibels (dB). 0.0f is default, -10f is quieter, -80f is almost mute.
		            gainControl.setValue(-25.0f);
		        }

		}catch(Exception e){
			
		}
	}
	
	public void play() {
		
		clip.start();
	}
	public void loop() {
	
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
	
		clip.stop();
	}
}
