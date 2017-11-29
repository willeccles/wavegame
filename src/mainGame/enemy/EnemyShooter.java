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

public class EnemyShooter extends GameObject {

	private Handler handler;
	private int sizeX;
	private int sizeY;
	private int timer;
	private GameObject player;
	private GameObject opponent;
	private double bulletVelX;
	private double bulletVelY;
	private int bulletSpeed;

	public EnemyShooter(double x, double y, int sizeX, int sizeY, int bulletSpeed, ID id, Handler handler) {
		super(Game.clampX(x, sizeX + 1), Game.clampY(y, sizeY + 1), id);
		this.handler = handler;
		this.velX = 0;
		this.velY = 0;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.timer = 60;
		this.bulletSpeed = bulletSpeed;

		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player)
				player = handler.object.get(i);
			if (handler.object.get(i).getId() == ID.Player2)
				opponent = handler.object.get(i);
		}

	}

	@Override
	public void tick() {
		timer--;
		handler.addObject(new Trail(x, y, ID.Trail, Color.yellow, this.sizeX, this.sizeY, 0.025, this.handler));
		if (timer <= 0) {
			shoot();
			updateEnemy();
			timer = 10;
		}
	}

	public void shoot() {
		double diffX = this.x - player.getX() - 16;
		double diffY = this.y - player.getY() - 16;
		double distance = Math.sqrt(((this.x - player.getX()) * (this.x - player.getX()))
				+ ((this.y - player.getY()) * (this.y - player.getY())));
		////////////////////////////// pythagorean theorem
		////////////////////////////// above//////////////////////////////////////////////////
		bulletVelX = ((this.bulletSpeed / distance) * diffX); // numerator affects speed of enemy
		bulletVelY = ((this.bulletSpeed / distance) * diffY);// numerator affects speed of enemy

		handler.addObject(new EnemyShooterBullet(this.x, this.y, bulletVelX, bulletVelY, ID.EnemyShooterBullet, this.handler, Color.yellow));

		if (handler.isMulti()) {
			diffX = this.x - opponent.getX() - 16;
			diffY = this.y - opponent.getY() - 16;
			distance = Math.sqrt(((this.x - opponent.getX()) * (this.x - opponent.getX()))
					+ ((this.y - opponent.getY()) * (this.y - opponent.getY())));
			////////////////////////////// pythagorean theorem
			////////////////////////////// above//////////////////////////////////////////////////
			bulletVelX = ((this.bulletSpeed / distance) * diffX); // numerator affects speed of enemy
			bulletVelY = ((this.bulletSpeed / distance) * diffY);// numerator affects speed of enemy

			handler.addObject(new EnemyShooterBullet(this.x, this.y, bulletVelX, bulletVelY, ID.EnemyShooterBullet, this.handler, Color.yellow));
		}
	}

	public void updateEnemy() {
		this.sizeX--;
		this.sizeY--;

		if (sizeX <= 0) {
			handler.removeObject(this);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect((int) x, (int) y, this.sizeX, this.sizeY);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, this.sizeX, this.sizeY);
	}

}
