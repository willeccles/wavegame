package mainGame;

import java.awt.Color;
import java.util.Random;

/**
 * A type of game mode in the game
 * 
 * @author Kyle Gorman 10/16/17
 *
 */

public class SpawnMultiplayer {

	private Handler handler;
	private HUD hud;
	private Game game;
	private int spawnTimer;
	private int differentEnemies;
	private Random r;
	private String[] side = {"left", "right", "top", "bottom"};
	private int trackerTimer;
	private Color trackerColor;
	private int count;

	public SpawnMultiplayer(Handler handler, HUD hud, Game game) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		handler.object.clear();
		hud.health = 100;
		hud.setScore(0);
		hud.setLevel(1);
		spawnTimer = 0;
		r = new Random();
		//different types of enemies added
		differentEnemies = 9;	
		trackerTimer = 1000;
		trackerColor = Color.blue;
		count = 0;
	}

	public void tick() {
		hud.tick();
		// updates the trackers color
		if(trackerTimer == 999) {
			trackerColor = Color.blue;
		} else if (trackerTimer == 500) {
			trackerColor = Color.black;
		} else if (trackerTimer == 0) {
			trackerTimer = 1000;
		}
		//prevents the trackers from spawning invisible 
		if(count == 1) {
			trackerTimer--;
		}
	}

	public void spawnEntity(ID type, double x, double y, int option, String side) {
		switch (type) {
			case EnemyBasic:
				handler.addObject(new EnemyBasic(x, y, 9, 9, type, handler));
				break;
			case EnemyFast:
				break;
			case EnemySmart:
				break;
			case EnemyBurst:
				break;
			case EnemySweep:
				break;
			case EnemyShooter:
				break;
			case EnemyTracker:
				break;
			case EnemyExpand:
				break;
			case EnemyMiniShooter:
				break;
			case EnemyMiniShooterBullet:
				break;
			case EnemyPorcupine:
				break;
		}
	}

	public int randInt() {
		return (int) (Math.random()*(differentEnemies));
	}

	public void restart() {
		spawnTimer = 0;
	}
}
