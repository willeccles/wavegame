package mainGame.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import mainGame.*;

/**
 * The main menu
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class PauseMenu {

	private Image img;

	public PauseMenu() {

		img = null;
		try {
			URL imageURL = Game.class.getResource("images/PauseMenu.png");
			img = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics g) {
		g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
	}
}
