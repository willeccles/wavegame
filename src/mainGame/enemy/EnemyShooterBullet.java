package mainGame.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import mainGame.*;
import mainGame.gfx.*;

/**
 * A type of enemy in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class EnemyShooterBullet extends GameObject {

	private Handler handler;
	private Color color;

	public EnemyShooterBullet(double x, double y, double velX, double velY, ID id, Handler handler, Color color) {
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
		this.color = color;
	}

	@Override
	public void tick() {
		this.x += velX;
		this.y += velY;

		// if (this.y <= 0 || this.y >= Game.HEIGHT - 40) velY *= -1;
		// if (this.x <= 0 || this.x >= Game.WIDTH - 16) velX *= -1;

		handler.addObject(new Trail(x, y, ID.Trail, color, 4, 4, 0.025, this.handler));

		// remove the bullet if it's off the screen
		if (this.x <= -4 || this.x >= 1280 || this.y <= -4 || this.y >= 720)
			handler.removeObject(this);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect((int) x, (int) y, 4, 4);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}

}
