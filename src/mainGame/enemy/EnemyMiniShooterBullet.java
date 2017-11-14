package mainGame.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import mainGame.*;
import mainGame.gfx.*;

/**
 * A type of enemy in the game
 * 
 * @author Eamon Duffy 10/19/17
 *
 */

public class EnemyMiniShooterBullet extends GameObject {

	private Handler handler;

	public EnemyMiniShooterBullet(double x, double y, double velX, double velY, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
	}

	public void tick() {
		this.x += velX;
		this.y += velY;

		// if (this.y <= 0 || this.y >= Game.HEIGHT - 40) velY *= -1;
		// if (this.x <= 0 || this.x >= Game.WIDTH - 16) velX *= -1;

		handler.addObject(new Trail(x, y, ID.Trail, Color.magenta, 4, 4, 0.025, this.handler));

		removeBullets();
	}

	public void removeBullets() {

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if (tempObject.getId() == ID.EnemyMiniShooterBullet) {
				if (tempObject.getX() >= Game.WIDTH || tempObject.getY() >= Game.HEIGHT || tempObject.getX() < 0 || tempObject.getY() < 0) {
					handler.removeObject(tempObject);
				}
			}

		}

	}

	public void render(Graphics g) {
		g.setColor(Color.magenta);
		g.fillRect((int) x, (int) y, 4, 4);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}

}

