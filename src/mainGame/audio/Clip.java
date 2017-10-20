package mainGame.audio;

import java.io.File;
import java.net.URI;
import java.awt.Toolkit;
import javafx.scene.media.Media;

public class Clip {
	private double volume; // double from 0.0-1.0 representing the relative volume to play the file at. Will be clamped at playback, so even 123.123 just = 1.0
	private Media media; // the media that will be played

	/**
	 * Constructor.
	 * @param filesource The path to the audio clip to play.
	 * @param volume The volume to play the file at (clamped to 0.0-1.0).
	 */
	public Clip(String filesource, double volume) {
		this.volume = volume;
		try {
			File f = new File(Game.class.getResource(filesource).toURI().toString());
			media = new Media(f.toString().replaceAll("\\\\", "/"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructor.
	 * @param filesource The path to the audio clip to play.
	 */
	public Clip(String filesource) {
		this(filesource, 1.0);
	}

	/**
	 * Get the playback volume for the clip.
	 * @return the playback volume as a double
	 */
	public double getVolume() {
		return volume;
	}

	/**
	 * Get the Media representing the audio file.
	 * @return the clip's Media for playback
	 */
	public Media getMedia() {
		return media;
	}
}
