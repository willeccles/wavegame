package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Random;

/**
 * Boss made by a retard
 * 
 * Kid can't even code... like honestly get good
 * 
 * @author Kyle Gorman 11/3/17
 *
 */

public class BossSeparates extends GameObject {

	Random r = new Random();
	private Image img;
	private int size;
	
	public BossSeparates(double x, double y, ID id, Handler handler, Player player, int size, int health, int velX, int velY) {
		super(x, y, id);
		this.velX = velX;
		this.velY = velY;
		img = getImage("images/EnemyBoss.png");
		this.health = health;
		this.size = size;
	}

	public void tick() {
		this.health -= 1;
		attackPlayer();
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

	public void render(Graphics g) {
			g.drawImage(img, (int) this.x, (int) this.y, size, size, null);
		// HEALTH BAR
		g.setColor(Color.GRAY);
		g.fillRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, 1000, 50);
		g.setColor(Color.RED);
		g.fillRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, this.health/2, 50);
		g.setColor(Color.WHITE);
		g.drawRect(Game.WIDTH / 2 - 500, Game.HEIGHT - 150, 1000, 50);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, size, size);
	}

	public void attackPlayer() {
		this.x += velX;
		this.y += velY;
		if (this.y <= 0 || this.y >= Game.HEIGHT - this.size)
			velY *= -1;
		if (this.x <= 0 || this.x >= Game.WIDTH - this.size)
			velX *= -1;
	}
}
