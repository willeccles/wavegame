package mainGame;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Every object in the game extends this abstract class
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public abstract class GameObject {

	// protected means that variables can only be accessed by things that extends
	// GameObject
	public double x, y;
	public ID id;
	public double velX, velY;
	public boolean isMoving;
	public int health;
	public GameObject(double x, double y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;

	}

	// abstract classes are needed in subclasses (need to be explicitly implemented)

	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract Rectangle getBounds();

	// non abstract can be accessed via OBJECT.________, does not need to be
	// implemented, but can be overidden

	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public double getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public double getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public int getHealth() {
		return this.health;
	}

}
