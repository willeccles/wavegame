package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;

/**
 * A type of enemy in the game
 * 
 * @author Eamon Duffy 11/1/17
 *
 */

public class RollBoss2 extends GameObject {

	private Handler handler;
	private Image img;

	public RollBoss2(double x, double y, int velX, int velY, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
		img = getImage("images/Angry-Balls2.png");
	}

	public void tick() {
		this.x += velX;
		this.y += velY;

		if (this.y <= 0 || this.y >= Game.HEIGHT - 11)
			velY *= -1;
		if (this.x <= 0 || this.x >= Game.WIDTH - 11)
			velX *= -1;
	}

	public void render(Graphics g) {
		g.drawImage(img, (int) this.x, (int) this.y, 96, 96, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 11, 11);
	}
	
	public Image getImage(String path) {
		Image image = null;
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return image;
	}

}