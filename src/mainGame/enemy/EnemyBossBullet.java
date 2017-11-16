package mainGame.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import mainGame.*;
import mainGame.gfx.*;

/**
 * The bullets that the first boss shoots
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class EnemyBossBullet extends GameObject {

	private Handler handler;
	Random r = new Random();
	private int max = 15;
	private int min = -15;

	public EnemyBossBullet(double x, double y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		velX = (r.nextInt((max - min) + 1) + min);// OFFICIAL WAY TO GET A RANGE FOR randInt()
		velY = 30;
		this.y -= velY; // this ensures that the bullet spawns where it needs to
	}

	public void tick() {
		this.x += velX;
		this.y += velY;

		handler.addObject(new Trail(x, y, ID.Trail, Color.red, 11, 11, 0.025, this.handler));
		
		// remove the bullet if it's off the screen
		if (this.x <= -4 || this.x >= 1280 || this.y <= -4 || this.y >= 720)
			handler.removeObject(this);
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int) x, (int) y, 11, 11);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 11, 11);
	}

}
