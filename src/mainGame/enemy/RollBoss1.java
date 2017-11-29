package mainGame.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import mainGame.*;

/**
 * A type of enemy in the game
 * 
 * @author Eamon Duffy 11/1/17
 *
 */

public class RollBoss1 extends GameObject {

	private Handler handler;
	private Image img;

	public RollBoss1(double x, double y, int velX, int velY, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
		img = getImage("images/Angry-Balls1.png");
		this.health = 2000;
	}

	@Override
	public void tick() {
		this.x += velX;
		this.y += velY;
		this.health -= 1;
		
		if (this.y <= 0 || this.y >= Game.HEIGHT - 235)
			velY *= -1;
		if (this.x <= 0 || this.x >= Game.WIDTH - 205)
			velX *= -1;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img, (int) this.x, (int) this.y, 300, 300, null);
		
		// HEALTH BAR
				g.setColor(Color.GRAY);
				g.fillRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, 800, 50);
				g.setColor(Color.RED);
				g.fillRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, this.health/2, 50);
				g.setColor(Color.WHITE);
				g.drawRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, 800, 50);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 250, 250);
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
