package mainGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import mainGame.Game.STATE;

/**
 * Contains the programming of levels 1-10, as well as handles level progression
 * 
 * @author Brandon Loehle 5/30/16
 */

public class Spawn1to5 {

	public static int LEVEL_SET = 1;
	private Handler handler;
	private HUD hud;
	private Game game;
	private int scoreKeep = 0;
	private Random r = new Random();
	private int spawnTimer;
	private int levelTimer;
	private String[] side = { "left", "right", "top", "bottom" };
	ArrayList<Integer> levels = new ArrayList<Integer>(); // MAKE THIS AN ARRAY LIST SO I CAN REMOVE OBJECTS
	private int index;
	private int levelsRemaining;
	private int levelNumber = 0;
	private int tempCounter = 0;
	private Color trackerColor;
	private int trackerTimer;

	public Spawn1to5(Handler handler, HUD hud, Game game) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		handler.object.clear();
		hud.health = 100;
		hud.setScore(0);
		hud.setLevel(1);
		spawnTimer = 10;
		levelTimer = 150;
		levelsRemaining = 5;
		hud.setLevel(1);
		tempCounter = 0;
		addLevels();
		index = r.nextInt(levelsRemaining);
		levelNumber = 0;
		trackerColor = Color.blue;
		trackerTimer = 1000;
	}

	/**
	 * Pre-load every level
	 */
	public void addLevels() {
		for (int i = 1; i <= 5; i++) {
			levels.add(i);
		}
	}

	/**
	 * Called once every 60 seconds by the Game loop
	 */
	public void tick() {
		if (levelNumber <= 0) {
			levelTimer--;
			if (tempCounter < 1) {// display intro game message ONE time
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, "Let's start off easy...",
						ID.Levels1to10Text));
				tempCounter++;
			}
			if (levelTimer <= 0) {// time to play!
				handler.clearEnemies();
				tempCounter = 0;
				levelNumber = levels.get(index);
				//will need to update how the levels are generated to allow for additional enemies
				//used for testing purposes for the new enemy
				//levelNumber = 6;
			}

		}
		/*
		 * EVERY LEVEL WORKS THE SAME WAY
		 * 
		 * Only the first level is commented
		 * 
		 * Please refer to this bit of code to understand how each level works
		 * 
		 */
		else if (levelNumber == 1) {// this is level 1
			spawnTimer--;// keep decrementing the spawning spawnTimer 60 times a second
			levelTimer--;// keep decrementing the level spawnTimer 60 times a second
			if (tempCounter < 1) {// called only once, but sets the levelTimer to how long we want this level to
				// run for
				levelTimer = 2000;// 2000 / 60 method calls a second = 33.33 seconds long
				tempCounter++;// ensures the method is only called once
			}
			if (spawnTimer == 0) {// time to spawn another enemy
				handler.addObject(
						new EnemyBasic(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 9, 9, ID.EnemyBasic, handler));
				// add them to the handler, which handles all game objects
				spawnTimer = 100;// reset the spawn timer
			}
			if (levelTimer == 0) {// level is over
				handler.clearEnemies();// clear the enemies
				hud.setLevel(hud.getLevel() + 1);// Increment level number on HUD
				spawnTimer = 40;
				tempCounter = 0;// reset tempCounter
				if (levelsRemaining == 1) {// time for the boss!
					levelNumber = 101;// arbitrary number for the boss level
				} else {// not time for the boss, just go to the next level
					levels.remove(index);// remove the current level from being selected
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);// pick another level at random
					levelNumber = levels.get(index);// set levelNumber to whatever index was randomly selected
				}
			}
		} else if (levelNumber == 2) {
			spawnTimer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 2000;
				tempCounter++;
			}
			if (spawnTimer == 30) {
				handler.addObject(
						new EnemySweep(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 15, 1, ID.EnemySweep, handler));
			} else if (spawnTimer == 20) {
				handler.addObject(
						new EnemySweep(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 15, -1, ID.EnemySweep, handler));
			} else if (spawnTimer == 10) {
				handler.addObject(
						new EnemySweep(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 15, 3, ID.EnemySweep, handler));
			} else if (spawnTimer == 0) {
				handler.addObject(
						new EnemySweep(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 15, -3, ID.EnemySweep, handler));
				spawnTimer = 80;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 3) {
			spawnTimer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1500;
				tempCounter++;
			}
			if (spawnTimer == 0) {
				handler.addObject(
						new EnemySmart(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), -5, ID.EnemySmart, handler));
				spawnTimer = 100;
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 4) {
			levelTimer--;
			if (tempCounter < 1) {
				handler.addObject(new EnemyShooter(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 100, 100,
						-20, ID.EnemyShooter, this.handler));
				levelTimer = 1300;
				tempCounter++;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 5) {
			spawnTimer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1400;
				tempCounter++;
			}
			if (spawnTimer <= 0) {
				handler.addObject(new EnemyBurst(-200, 200, 50, 50, 200, side[r.nextInt(4)], ID.EnemyBurst, handler));
				spawnTimer = 180;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				spawnTimer = 10;
				tempCounter = 0;
				if (levelsRemaining == 1) {
					levelNumber = 101;
				} else {
					levels.remove(index);
					levelsRemaining--;
					index = r.nextInt(levelsRemaining);
					levelNumber = levels.get(index);
				}
			}
		} else if (levelNumber == 6){
			spawnTimer--;
			if(trackerTimer == 999){
				trackerColor = Color.blue;
			} else if (trackerTimer == 500){
				trackerColor = Color.white;
			} else if (trackerTimer == 0){
				trackerTimer = 1000;
			}
			trackerTimer--;
			if(spawnTimer == 0){
			handler.addObject(
					new EnemyTracker(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), -5, ID.EnemyTracker, handler, trackerColor));
			spawnTimer = 100;
			}
		}
		
		else if (levelNumber == 101) {// arbitrary number for the boss
			if (tempCounter < 1) {
				handler.addObject(new EnemyBoss(ID.EnemyBoss, handler));
				tempCounter++;
			} else if (tempCounter >= 1) {
				for (int i = 0; i < handler.object.size(); i++) {
					GameObject tempObject = handler.object.get(i);
					if (tempObject.getId() == ID.EnemyBoss) {
						if (tempObject.getHealth() <= 0) {
							handler.removeObject(tempObject);
							LEVEL_SET++;
							game.gameState = STATE.Upgrade;
						}
					}
				}
			}

		}

	}

	public void skipLevel() {
		if (levelsRemaining == 1) {
			tempCounter = 0;
			levelNumber = 101;
		} else if (levelsRemaining > 1) {
			levels.remove(index);
			levelsRemaining--;
			tempCounter = 0;
			index = r.nextInt(levelsRemaining);
			levelNumber = levels.get(index);
		}
	}

	public void restart() {
		levelNumber = -5;
		tempCounter = 0;
		levelTimer = 150;
		levelsRemaining = 5;
		index = r.nextInt(levelsRemaining);
	}

}
