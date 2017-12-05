package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import mainGame.Game.STATE;
import mainGame.gui.*;
import mainGame.gfx.*;
import mainGame.pickup.*;

/**
 * The main player in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */
public class Player extends GameObject {

	Random r = new Random();
	Handler handler;
	private HUD hud;
	private Game game;
	private int damage;
	private int playerWidth, playerHeight;
	public static int playerSpeed = 10;
	public String gameMode;
	private Color color;
	private Color tailcolor;
	private boolean isOpponent;
	int count;

	/**
	 * Use the other constructor unless this is an opponent in multiplayer.
	 */
	public Player(double x, double y, ID id, Handler handler, HUD hud, Game game, Color c, boolean isOpponent) {
		super(x, y, id);
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		this.damage = 2;
		this.color = c;
		this.tailcolor = c;
		this.isOpponent = isOpponent;
		playerWidth = 21;
		playerHeight = 21;
		count = 0;
	}

	/**
	 * Old constructor doesn't take a color.
	 */
	public Player(double x, double y, ID id, Handler handler, HUD hud, Game game) {
		this(x, y, id, handler, hud, game, Color.white, false);
	}

	@Override
	public void tick() {
		// add the trail that follows it
		if ((velX != 0 || velY != 0))
			handler.addObject(new Trail(x, y, ID.Trail, this.tailcolor, playerWidth, playerHeight, 0.05, this.handler));

		this.x += velX;
		this.y += velY;
		x = Game.clampX(x, playerWidth);
		y = Game.clampY(y, playerHeight);

		// these things will be done by the other player's client, so if it's the opponent player we don't care.
		if (!isOpponent) {
			collision();
			checkIfDead();
		}
	}
	public String checkGame() {
		return gameMode;
	}
	public void checkIfDead() {
		if (hud.health <= 0) {// player is dead, game over!

			if (hud.getExtraLives() == 0) {
				this.reset();
				if(game.gameState == STATE.Survival) {
					gameMode = "survival";
				} else if(game.gameState == STATE.Wave) {
					gameMode = "waves";
				} else if(game.gameState == STATE.Bosses) {
					gameMode = "bosses";
				} else if(game.gameState == STATE.Multiplayer) {
					gameMode = "multiplayer";
				}
				if (game.gameState != STATE.Multiplayer) {
					game.gameState = STATE.GameOver;
				}
			}

			else if (hud.getExtraLives() > 0) {// has an extra life, game continues
				hud.setExtraLives(hud.getExtraLives() - 1);
				hud.setHealth(100);
			}
		}
	}

	/**
	 * Checks for collisions with all of the enemies, and handles it accordingly
	 */
	public void collision() {
		hud.updateScoreColor(Color.white);
		//for enemies
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if (tempObject.getId() == ID.EnemyBasic || tempObject.getId() == ID.EnemyFast || tempObject.getId() == ID.EnemySmart 
					|| tempObject.getId() == ID.EnemyBossBullet || tempObject.getId() == ID.EnemySweep || tempObject.getId() == ID.EnemyShooterBullet 
					|| tempObject.getId() == ID.EnemyBurst || tempObject.getId() == ID.EnemyShooter ||tempObject.getId() == ID.EnemyTracker 
					|| tempObject.getId() == ID.EnemyExpand || tempObject.getId() == ID.EnemyMiniShooter 
					|| tempObject.getId() == ID.EnemyMiniShooterBullet || tempObject.getId() == ID.EnemyPorcupine 
					|| tempObject.getId() == ID.EnemyMove) {// tempObject is an enemy
				// collision code
				if (getBounds().intersects(tempObject.getBounds())) {// player hit an enemy
					hud.health -= damage;
					game.soundClip.play();
					hud.updateScoreColor(Color.red);
				}

			}
			if (tempObject.getId() == ID.EnemyBoss) {
				// Allows player time to get out of upper area where they will get hurt once the
				// boss starts moving
				if (this.y <= 96 && tempObject.isMoving) {
					hud.health -= 2;
					game.soundClip.play();
					hud.updateScoreColor(Color.red);
				}

			}
			if (tempObject.getId() == ID.RollBoss1 || tempObject.getId() == ID.RollBoss2 
					|| tempObject.getId() == ID.BullBoss || tempObject.getId() == ID.SeparateBoss3 
					|| tempObject.getId() == ID.SeparateBoss || tempObject.getId() == ID.EnemyBoss
					|| tempObject.getId() == ID.SeparateBoss2 ) {
				//gives the player two seconds to avoid taking damage
				count++;
				if( getBounds().intersects(tempObject.getBounds()) && count > 120) {
					hud.health -= damage;
				}

			}

			if (tempObject.getId() == ID.BossEye) {
				count++;
				if( getBounds().intersects(tempObject.getBounds()) && count > 1080) {
					hud.health -= damage;
				}
			}

		}
		//for pickups
		for (int i = 0; i < handler.pickups.size(); i++) {
			Pickup tempPickup = handler.pickups.get(i);

			if (tempPickup.getId() == ID.HealthPickup) {

				if(getBounds().intersects(tempPickup.getBounds())) {
					if(hud.getHealthMax() - hud.health > 25) {
						hud.health += 25;
					} else {
						hud.health = hud.getHealthMax();
					}
					handler.removePickup(tempPickup);
				}
			} else if (tempPickup.getId() == ID.SpeedPickup) {
				if(getBounds().intersects(tempPickup.getBounds())) {
					playerSpeed += 1;
					handler.removePickup(tempPickup);
				}
			} else if (tempPickup.getId() == ID.ScorePickup) {
				if(getBounds().intersects(tempPickup.getBounds())) {
					hud.setScore(hud.getScore()+1000);
					handler.removePickup(tempPickup);
				}
			}
		}
	}


	@Override
	public void render(Graphics g) {

		g.setColor(this.color);
		g.fillRect((int) x, (int) y, playerWidth, playerHeight);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, playerWidth, playerHeight);
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setPlayerSize(int size) {
		this.playerWidth = size;
		this.playerHeight = size;
	}
	@Override
	public double getVelX() {
		return velX;
	}

	@Override
	public void setVelX(int velX) {
		this.velX = velX;
	}

	@Override
	public double getVelY() {
		return velY;
	}

	@Override
	public void setVelY(int velY) {
		this.velY = velY;
	}

	public void resetVel() {
		this.velY = 0;
		this.velX = 0;
	}

	public void resetLoc() {
		x = Game.WIDTH / 2 - 21;
		y = Game.HEIGHT / 2 - 21;
	}

	public void updateColors(Color head, Color tail) {
		if (!isOpponent) {
			if (head != null)
				this.color = head;
			if (tail != null)
				this.tailcolor = tail;
		}
	}
	//resets the counter after each boss
	public void resetCount() {
		count = 0;
	}

	public void reset() {
		this.resetCount();
		this.resetLoc();
		this.resetVel();
	}

}
