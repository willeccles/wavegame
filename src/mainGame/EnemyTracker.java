package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EnemyTracker extends GameObject {

	private Handler handler;
	private GameObject player;
	private int speed;
	private Color enemyColor;
	private int timer;

	public EnemyTracker(double x, double y, int speed, ID id, Handler handler, Color enemyColor, int timer) {
		super(x, y, id);
		this.handler = handler;
		this.speed = speed;
		this.enemyColor = enemyColor;
		this.timer = timer;

		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player)
				player = handler.object.get(i);
		}

	}

	public void tick() {
		this.x += velX;
		this.y += velY;
		////////////////////////////// pythagorean theorem
		////////////////////////////// below//////////////////////////////////////////////////
		double diffX = this.x - player.getX() - 16;
		double diffY = this.y - player.getY() - 16;
		double distance = Math.sqrt(((this.x - player.getX()) * (this.x - player.getX()))
				+ ((this.y - player.getY()) * (this.y - player.getY())));
		////////////////////////////// pythagorean theorem
		////////////////////////////// above//////////////////////////////////////////////////
		velX = ((this.speed / distance) * diffX); // numerator affects speed of enemy
		velY = ((this.speed / distance) * diffY);// numerator affects speed of enemy

		// if (this.y <= 0 || this.y >= Game.HEIGHT - 40) velY *= -1;
		// if (this.x <= 0 || this.x >= Game.WIDTH - 16) velX *= -1;
		if(timer == 999){
			enemyColor = Color.blue;
		} else if (timer == 500){
			enemyColor = Color.black;
		} else if (timer == 0){
			timer = 1000;
		}
		timer--;
		handler.addObject(new Trail(x, y, ID.Trail, enemyColor, 16, 16, 0.025, this.handler));
	}

	public void render(Graphics g) {
		g.setColor(enemyColor);
		g.fillRect((int) x, (int) y, 16, 16);

	}
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}

}