package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * A type of enemy in the game
 * 
 * @author Eamon Duffy 10/18/17
 *
 */

public class EnemyExpand extends GameObject {

	private Handler handler;
	private int sizeX;
	private int sizeY;
	private int timer;
	private GameObject player;

	public EnemyExpand(double x, double y, int sizeX, int sizeY, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.velX = 0;
		this.velY = 0;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.timer = 60;

		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player)
				player = handler.object.get(i);
		}
	}

	public void tick() {
		this.x += velX;
		this.y += velY;

		if (this.y <= 0 || this.y >= Game.HEIGHT - 40)
			velY *= -1;
		if (this.x <= 0 || this.x >= Game.WIDTH - 16)
			velX *= -1;

		handler.addObject(new Trail(x, y, ID.Trail, Color.pink, this.sizeX, this.sizeY, 0.025, this.handler));

		timer--;
		if (timer <= 0) {
			updateEnemy();
			timer = 10;
		}

	}

	public void updateEnemy() {
		this.sizeX++;
		this.sizeY++;
		
		if (sizeX == 300) {
			this.handler.removeObject(this);
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.pink);
		g.fillRect((int) x, (int) y, this.sizeX, this.sizeY);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, this.sizeX, this.sizeY);
	}

}
