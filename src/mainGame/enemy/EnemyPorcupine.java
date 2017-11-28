package mainGame.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import mainGame.*;
import mainGame.Game.STATE;
import mainGame.gfx.*;
/**
 * A type of enemy in the game
 * 
 * @author Kyle Gorman 10/19/17
 *
 */

public class EnemyPorcupine extends GameObject {

	private Handler handler;
	private int sizeX;
	private int sizeY;
	private int size;
	private int timer;
	private GameObject player;
	private int speed;
	private double bulletVelX;
	private double bulletVelY;
	private int bulletSpeed;
	private double distance;
	private double diffX;
	private double diffY;
	private Game game;

	public EnemyPorcupine(double x, double y, int sizeX, int sizeY, ID id, 
			Handler handler, int speed, int bulletSpeed, Game game) {
		super(x, y, id);
		this.handler = handler;
		this.velX = 0;
		this.velY = 0;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.timer = 60;
		this.speed = speed;
		this.size = sizeX;
		this.bulletSpeed = bulletSpeed;
		this.game = game;

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

		handler.addObject(new Trail(x, y, ID.Trail, Color.orange, this.sizeX, this.sizeY, 0.025, this.handler));

		this.x += velX;
		this.y += velY;
		////////////////////////////// pythagorean theorem
		////////////////////////////// below//////////////////////////////////////////////////
		diffX = this.x - player.getX() - 16;
		diffY = this.y - player.getY() - 16;
		distance = Math.sqrt(((this.x - player.getX()) * (this.x - player.getX()))
				+ ((this.y - player.getY()) * (this.y - player.getY())));
		////////////////////////////// pythagorean theorem
		////////////////////////////// above//////////////////////////////////////////////////

		move();

		timer--;
		if (timer <= 0) {
			shoot();
			if(this.game.gameState == STATE.Survival) {
				updateEnemy();
			}
			timer = 10;
		}

	}

	private void move() {
		// TODO Auto-generated method stub

		velX = ((this.speed / distance) * diffX); // numerator affects speed of enemy
		velY = ((this.speed / distance) * diffY);// numerator affects speed of enemy
	}

	private void shoot() {
		bulletVelX = ((this.bulletSpeed / distance) * diffX); // numerator affects speed of enemy
		bulletVelY = ((this.bulletSpeed / distance) * diffY);// numerator affects speed of enemy

		handler.addObject(new EnemyShooterBullet(this.x, this.y, bulletVelX, bulletVelY, ID.EnemyShooterBullet, this.handler, Color.orange));

	}

	public void updateEnemy() {
		this.sizeX--;
		this.sizeY--;

		if(sizeX <= 0) {
			handler.removeObject(this);
		}
	}
	public void render(Graphics g) {
		g.setColor(Color.orange);
		g.fillRect((int) x, (int) y, this.sizeX, this.sizeY);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, this.sizeX, this.sizeY);
	}

}
