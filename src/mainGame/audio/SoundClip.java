package mainGame.audio;

import java.io.File;
import java.net.URI;
import java.awt.Toolkit;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import mainGame.Game;

/**
 * A sound clip that is played asynchronously.
 * @author Will Eccles
 * @version 2017-10-20
 */
public class SoundClip {
	private double volume; // double from 0.0-1.0 representing the relative volume to play the file at. Will be clamped at playback, so even 123.123 just = 1.0
	private Media media; // the media that will be played
	private MediaPlayer player;

	/**
	 * Constructor.
	 * @param filesource The path to the audio clip to play.
	 * @param volume The volume to play the file at (clamped to 0.0-1.0).
	 */
	public SoundClip(String filesource, double volume) {
		this.volume = volume;
		try {
			File f = new File(Game.class.getResource(filesource).toURI().toString());
			media = new Media(f.toString().replaceAll("\\\\", "/"));
			player = new MediaPlayer(media);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructor.
	 * @param filesource The path to the audio clip to play.
	 */
	public SoundClip(String filesource) {
		this(filesource, 1.0);
	}

	/**
	 * Play the sound clip one time.
	 */
	public void play() {
		if (player.getStatus() != MediaPlayer.Status.PLAYING) {
			Thread t = new Thread(() -> {
				try {
					player.setVolume(volume);
					player.play();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			t.start();
		}
	}
}
