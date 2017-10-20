package mainGame.audio;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Toolkit;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import mainGame.Game;

/**
 * A class made for playing sound clips.
 * @author Will Eccles
 */
public class ClipPlayer {
	
	public ClipPlayer() {
		super(soundfile, false);
	}

	/**
	 * Play a thread to play a given clip once.
	 * @param clip The clip to be played.
	 */
	public void play(Clip clip) {
		Thread t = new Thread(() -> {
			try {
				player = new MediaPlayer(clip.getMedia());
				player.setVolume(clip.getVolume());
				player.play();
				t.join();
			} catch (Exception e) {
				// if there is an error, force the thread to join
				e.printStackTrace();
				try {
					t.join();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		t.start();
	}
}
