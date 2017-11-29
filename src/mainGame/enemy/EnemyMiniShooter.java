package mainGame.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import mainGame.*;
import mainGame.Game.STATE;
import mainGame.gfx.*;

/**
 * A type of enemy in the game
 * 
 * @author Eamon Duffy 10/19/17
 *
 */

public class EnemyMiniShooter extends GameObject {

	private Handler handler;
	private int sizeX;
	private int sizeY;
	private int timer;
	private GameObject player;
	private GameObject opponent; // used in multiplayer
	private double bulletVelX;
	private double bulletVelY;
	private int bulletSpeed;
	private Game game;

	public EnemyMiniShooter(double x, double y, int sizeX, int sizeY, 
			int bulletSpeed, ID id, Handler handler, Game game) {
		super(x, y, id);
		this.handler = handler;
		this.velX = 0;
		this.velY = 0;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.timer = 60;
		this.bulletSpeed = bulletSpeed;
		this.game = game;

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
		handler.addObject(new Trail(x, y, ID.Trail, Color.magenta, this.sizeX, this.sizeY, 0.025, this.handler));
		if (timer <= 0) {
			shoot();
			if (handler.isMulti() || game.gameState == STATE.Survival)
				updateEnemy();
			timer = 50;
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

		handler.addObject(new EnemyMiniShooterBullet(this.x, this.y, bulletVelX, bulletVelY, ID.EnemyMiniShooterBullet, this.handler));
		
		// if we are playing multiplayer, just go ahead and make another bullet to fire at the other player. This is going to be quite resource-intensive, so we need to make sure enemies aren't TOO cluttered on the screen.
		if (handler.isMulti()) {
			diffX = this.x - opponent.getX() - 16;
			diffY = this.y - opponent.getY() - 16;
			distance = Math.sqrt(((this.x - opponent.getX()) * (this.x - opponent.getX()))
					+ ((this.y - opponent.getY()) * (this.y - opponent.getY())));
			////////////////////////////// pythagorean theorem
			////////////////////////////// above//////////////////////////////////////////////////
			bulletVelX = ((this.bulletSpeed / distance) * diffX); // numerator affects speed of enemy
			bulletVelY = ((this.bulletSpeed / distance) * diffY);// numerator affects speed of enemy

			handler.addObject(new EnemyMiniShooterBullet(this.x, this.y, bulletVelX, bulletVelY, ID.EnemyMiniShooterBullet, this.handler));
		}
	}

	public void updateEnemy() {
		this.sizeX -= 5;
		this.sizeY -= 5;

		if (sizeX <= 0) {
			handler.removeObject(this);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.magenta);
		g.fillRect((int) x, (int) y, this.sizeX, this.sizeY);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, this.sizeX, this.sizeY);
	}

}
