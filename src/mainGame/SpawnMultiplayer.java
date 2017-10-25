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
		//different types of enemies added
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
			case ID.EnemyBasic:
				handler.addObject(new EnemyBasic(x, y, 9, 9, type, handler));
				break;
			case ID.EnemySmart:
				handler.addObject(new EnemySmart(x, y, -5, type, handler));
				break;
			case ID.EnemyBurst:
				handler.addObject(new EnemyBurst(x, y, 50, 50, 200, side, type, handler, true));
				break;
			case ID.EnemySweep:
				switch (option) {
					case 0:
						handler.addObject(new EnemySweep(x, y, 15, 1, type, handler));
						break;
					case 1:
						handler.addObject(new EnemySweep(x, y, 15, -1, type, handler));
						break;
					case 2:
						handler.addObject(new EnemySweep(x, y, 15, 3, type, handler));
						break;
					case 3:
						handler.addObject(new EnemySweep(x, y, 15, -3, type, handler));
						break;
				}
				break;
			case ID.EnemyShooter:
				handler.addObject(new EnemyShooter(x, y, 100, 100, -20, type, handler));
				break;
			case ID.EnemyTracker:
				count = 1;
				handler.addObject(new EnemyTracker(x, y, -5, type, handler, trackerColor, trackerTimer));
				break;
			case ID.EnemyExpand:
				handler.addObject(new EnemyExpand(Game.clampX(x, 100), Game.clampY(y, 100), 100, 100, type, handler));
				break;
			case ID.EnemyMiniShooter:
				handler.addObject(new EnemyMiniShooter(Game.clampX(x, 75), Game.clampY(y, 75), 75, 75, -10, type, handler));
				break;
			case ID.EnemyPorcupine:
				handler.addObject(new EnemyPorcupine(Game.clampX(x, 100), Game.clampY(y, 100), 100, 100, -10, type, handler));
				break;
		}
	}
}
