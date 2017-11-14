package mainGame.pickup;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import mainGame.*;

/**
 * Adds health to the player when they move over it
 * @author Kyle Gorman
 * 11/2/17
 *
 */

public class PickupScore extends Pickup {

	private Handler handler;
	private Image img;

	public PickupScore(double x, double y, ID id, String path, Handler handler) {
		super(x, y, id, path);
		this.handler = handler;

		img = null;
		try {
			URL imageURL = Game.class.getResource("images/coin.png");
			img = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void tick() {


	}


	public void render(Graphics g) {
		g.drawImage(this.img, (int)this.x, (int)this.y, 32, 32, null);

	}


	public Rectangle getBounds() {

		return new Rectangle((int) this.x, (int) this.y, 32, 32);

	}


}
