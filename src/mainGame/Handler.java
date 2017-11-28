package mainGame;

import java.awt.Graphics;
import java.util.ArrayList;

import mainGame.Game.STATE;
import mainGame.gfx.*;
import mainGame.pickup.*;

/**
 * Class used for containing every instance of GameObject. These include all
 * enemies and players
 * 
 * @author Brandon Loehle 5/30/16
 */
public class Handler {

	public ArrayList<GameObject> object = new ArrayList<GameObject>();
	public ArrayList<Pickup> pickups = new ArrayList<Pickup>();
	private static int timer = 0;
	private static boolean isMP = false;
	private Game game;
	
	public Handler(Game game) {
		this.game = game;
	}

	/**
	 * Updates each entity in the game by looping through each ArrayList and
	 * calling the tick() function on each object
	 */
	public void tick() {
		if (getTimer() > 0) {
			setTimer(getTimer() - 1);
		}
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			if (tempObject == null) return;
			if (tempObject.getId() == ID.Player || tempObject.getId() == ID.Player2 || tempObject.getId() == ID.Trail
					|| tempObject.getId() == ID.EnemyBurstWarning) {
				// we don't want these to ever be frozen by the Screen Freeze ability

				// Every GameObject has a tick method, so this effectively
				// updates every single object
				tempObject.tick();

			} else {
				if (getTimer() <= 0) {// if Screen Freeze power-up is unlocked, enemy
					// ID's will pause for the length of the
					// timer, and not update
					tempObject.tick();
				}
			}
		}
		for (int i = 0; i < pickups.size(); i++) {
			Pickup tempObject = pickups.get(i);

			// Every Pickup has a tick method, so this effectively updates every
			// single object
			tempObject.tick();
		}

	}

	/**
	 * Redraws each entity in the game by looping through each ArrayList and
	 * calling the tick() function on each object
	 */
	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			if (tempObject == null) return;
			if (tempObject.getId() == ID.Firework || tempObject.getId() == ID.FireworkSpark) {
				if(game.gameState == STATE.Menu)
					tempObject.render(g);
			} else tempObject.render(g);
		}
		for (int i = 0; i < pickups.size(); i++) {
			Pickup tempObject = pickups.get(i);

			tempObject.render(g);
		}
	}

	public void pause() {
		setTimer(120);
	}

	public boolean isPaused() {
		return (getTimer() > 0);
	}

	public void addObject(GameObject object) {
		this.object.add(object);
	}

	public void removeObject(GameObject object) {
		this.object.remove(object);
	}

	public void addPickup(Pickup object) {
		this.pickups.add(object);
	}

	public void removePickup(Pickup object) {
		this.pickups.remove(object);
	}

	/**
	 * Clears all entities that have an ID of some sort of enemy
	 */
	public void clearEnemies() {
		for (int i = 0; i < this.object.size(); i++) {
			GameObject tempObject = this.object.get(i);
			if (tempObject.getId() != ID.Player && tempObject.getId() != ID.Player2) {
				this.removeObject(tempObject);
				i--; // Removing shrinks the array by 1, causing the loop to
				// skip an enemy
			}
		}
	}

	/**
	 * Clears all entities that have an ID of player
	 */
	public void clearPlayer() {
		for (int i = 0; i < this.object.size(); i++) {
			GameObject tempObject = this.object.get(i);
			if (tempObject.getId() == ID.Player || tempObject.getId() == ID.Player2) {
				this.removeObject(tempObject);
				i--; // Removing shrinks the array by 1, causing the loop to
				// skip a player (should there be more than one)
			}
		}
	}

	public boolean isMulti() {
		return isMP;
	}

	public void setMulti(boolean b) {
		isMP = b;
	}

	/**
	 * @return the timer
	 */
	public static int getTimer() {
		return timer;
	}

	/**
	 * @param timer the timer to set
	 */
	public static void setTimer(int timer) {
		Handler.timer = timer;
	}
}
