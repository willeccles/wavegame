package mainGame.audio;

import java.io.File;
import java.net.URISyntaxException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import mainGame.Game;

public class SoundPlayer extends Thread {
	private String soundfile;
	//private Player player;
	private static MediaPlayer player;
	private boolean repeats = false;

	/**
	 * Create a soundplayer with a songfile and whether or not it should repeat.
	 * @param songfile The path to the file.
	 * @param repeat Whether or not the player should repeat when the file ends.
	 */
	public SoundPlayer(String songfile, boolean repeat) {
		soundfile = songfile;
		repeats = repeat;
	}

	/**
	 * Create a soundplayer with just a given songfile that doesn't repeat.
	 * @param songfile The path of the file.
	 */
	public SoundPlayer(String songfile) {
		this(songfile, false);
	}

	@Override
	public void run() {
		try {
			File f = new File(Game.class.getResource(soundfile).toURI().toString());
			Media song = new Media(f.toString().replaceAll("\\\\", "/"));
			player = new MediaPlayer(song);
			player.setVolume(0.25);
			// this will make the player repeat
			player.setOnEndOfMedia(() -> {
				if (repeats) {
					player.seek(Duration.ZERO); // goes back to the beginning of the song
				}
			});
			player.play();
		} catch (URISyntaxException use) {
			use.printStackTrace();
		}
	}
	
	public void setSong(String song) {
		soundfile = song;
	}

	public String getSong() { 
		return soundfile;
	}

	public void setRepeat(boolean r) {
		repeats = r;
	}

	public boolean doesRepeat() {
		return repeats;
	}

	public void play() {
		// just in case the thread is not started
		if (!this.isAlive()) {
			try {
				this.start();
			} catch (IllegalThreadStateException itse) {
			}
		}
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
	
	public boolean isPaused() {
		if (player == null) return false;
		return (player.getStatus() == MediaPlayer.Status.PAUSED);
	}
}
