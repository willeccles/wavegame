package mainGame;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Toolkit;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundPlayer extends Thread {
	private String soundfile;
	//private Player player;
	private MediaPlayer player;

	public SoundPlayer(String songfile) {
		soundfile = songfile;
	}

	public void run() {
		try {
			File f = new File(Game.class.getResource(soundfile).toURI().toString());
			Media song = new Media(f.toString().replaceAll("\\\\", "/"));
			player = new MediaPlayer(song);
			player.setVolume(0.25);
			player.play();
		} catch (URISyntaxException use) {
			use.printStackTrace();
		}
	}

	public void play() {
		player.play();
	}

	public void pause() {
		player.pause();
	}

	public void stop_playing() {
		player.stop();
		try {
			this.join();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
}
