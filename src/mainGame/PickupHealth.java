package mainGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;

/**
 * Adds health to the player when they move over it
 * @author Kyle Gorman
 * 10/25/17
 *
 */

public class PickupHealth extends Pickup{
	
	private Handler handler;
	private Image img;
	
	public PickupHealth(double x, double y, ID id, String path, Handler handler) {
		super(x, y, id, path);
		this.handler = handler;
		
		img = null;
		try {
			URL imageURL = Game.class.getResource("images/ham.png");
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
