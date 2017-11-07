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

public class BossKyle extends GameObject {

	private Handler handler;
	private int timer = 80;
	private int timer2 = 50;
	Random r = new Random();
	private Image img;
	private int speed;
	private int bulletSpeed;
	private double diffX;
	private double diffY;
	private double distance;
	private Player player;
	private double bulletVelX;
	private double bulletVelY;
	
	public BossKyle(double x, double y, ID id, Handler handler, Player player) {
		super(x, y, id);
		this.handler = handler;
		this.velX = 0;
		this.velY = 0;
		this.speed = -3;
		this.bulletSpeed = -6;
		this.player = player;
		img = getImage("images/EnemyBoss.png");
		this.health = 2000;
	}

	public void tick() {
		this.health -= 1;
		this.x += velX;
		this.y += velY;

		if (this.y <= 0 || this.y >= Game.HEIGHT - 40)
			velY *= -1;
		if (this.x <= 0 || this.x >= Game.WIDTH - 16)
			velX *= -1;

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
			updateEnemy();
			timer = 100;
		}

	}
	private void move() {
		// TODO Auto-generated method stub

		velX = ((this.speed / distance) * diffX); // numerator affects speed of enemy
		velY = ((this.speed / distance) * diffY);// numerator affects speed of enemy
	}

	private void shoot() {
		//bulletVelX = ((this.bulletSpeed / distance) * diffX); // numerator affects speed of enemy
		//bulletVelY = ((this.bulletSpeed / distance) * diffY);// numerator affects speed of enemy

		//handler.addObject(
				//new EnemyShooterBullet(this.x, this.y, bulletVelX, bulletVelY, ID.EnemyShooterBullet, this.handler, Color.orange));
		handler.addObject(
				new EnemyBurst(x, y, bulletVelX, bulletVelY, 200, "left", ID.EnemyBurst, handler));
	}
	
	private void updateEnemy() {
		
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
		g.drawImage(img, (int) this.x, (int) this.y, 96, 96, null);

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
		return new Rectangle((int) this.x, (int) this.y, 96, 96);
	}

	// allows for grey line to be drawn, as well as first bullet shot
	public void drawFirstBullet() {
		if (timer2 == 1)
			handler.addObject(new EnemyBossBullet((int) this.x + 48, (int) this.y + 96, ID.EnemyBossBullet, handler));
	}

}
