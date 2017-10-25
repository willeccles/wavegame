package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 * A type of enemy in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class EnemyBurst extends GameObject {

	private Handler handler;
	private int timer;
	private int size;
	private String side;
	private Random r = new Random();
	private boolean isMultiplayer = false;

	/**
	 * Constructor.
	 * @param mult ONLY set this to true if you're using this in multiplayer.
	 */
	public EnemyBurst(double x, double y, double velX, double velY, int size, String side, ID id, Handler handler, boolean mult) {
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
		this.timer = 60;
		this.side = side;
		this.size = size;
		isMultiplayer = mult;

		if (this.side.equals("left")) {
			handler.object.add(new EnemyBurstWarning(0, 0, 25, Game.HEIGHT, ID.EnemyBurstWarning, handler));
			setPos();
			setVel();
		} else if (this.side.equals("right")) {
			handler.object.add(new EnemyBurstWarning(Game.WIDTH - 25, 0, 25, Game.HEIGHT, ID.EnemyBurstWarning, handler));
			setPos();
			setVel();
		} else if (this.side.equals("top")) {
			handler.object.add(new EnemyBurstWarning(0, 0, Game.WIDTH, 25, ID.EnemyBurstWarning, handler));
			setPos();
			setVel();
		} else if (this.side.equals("bottom")) {
			handler.object.add(new EnemyBurstWarning(0, Game.HEIGHT - 25, Game.WIDTH, 25, ID.EnemyBurstWarning, handler));
			setPos();
			setVel();
		}
	}
	
	public EnemyBurst(double x, double y, double velX, double velY, int size, String side, ID id, Handler handler) {
		this(x, y, velX, velY, size, side, id, handler, false);
	}

	public void tick() {

		handler.addObject(new Trail(x, y, ID.Trail, Color.orange, this.size, this.size, 0.025, this.handler));

		timer--;
		if (timer <= 0) {
			this.x += velX;
			this.y += velY;
		}

		// MASSIVE reduction in memory usage from handler. at the end before it had about 375+ objects, now it ends with 22 or so
		if (this.side.equals("left") && this.x > Game.WIDTH) {
			handler.removeObject(this);
		} else if (this.side.equals("right") && this.x < -(size)) {
			handler.removeObject(this);
		} else if (this.side.equals("top") && this.y > Game.HEIGHT) {
			handler.removeObject(this);
		} else if (this.side.equals("bottom") && this.y < -(size)) {
			handler.removeObject(this);
		}

	}

	public void setPos() {
		if (this.side.equals("left")) {
			if (isMultiplayer)
				this.y = Game.clampY(this.y, 200);
			else
				this.y = r.nextInt(((Game.HEIGHT - size) - 0) + 1) + 0;
		} else if (this.side.equals("right")) {
			if (isMultiplayer)
				this.y = Game.clampY(this.y, 200);
			else
				this.y = r.nextInt(((Game.HEIGHT - size) - 0) + 1) + 0;
			this.x = Game.WIDTH + 200;
		} else if (this.side.equals("top")) {
			this.y = -(size);
			if (isMultiplayer)
				this.x = Game.clampX(this.x, 200);
			else
				this.x = r.nextInt(((Game.WIDTH - size) - 0) + 1) + 0;
		} else if (this.side.equals("bottom")) {
			this.y = Game.HEIGHT + 200;
			if (isMultiplayer)
				this.x = Game.clampX(this.x, 200);
			else
				this.x = r.nextInt(((Game.WIDTH - size) - 0) + 1) + 0;
		}
	}

	public void setVel() {
		if (this.side.equals("left")) {
			this.velY = 0;
		} else if (this.side.equals("right")) {
			this.velX = -(this.velX);
			this.velY = 0;

		} else if (this.side.equals("top")) {
			this.velX = 0;

		} else if (this.side.equals("bottom")) {
			this.velX = 0;
			this.velY = -(this.velY);
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.orange);
		g.fillRect((int) x, (int) y, this.size, this.size);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 200, 200);
	}

}
