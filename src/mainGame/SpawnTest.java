package mainGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import mainGame.Game.STATE;


public class SpawnTest {

	public static int LEVEL_SET = 1;
	private Handler handler;
	private HUD hud;
	private Game game;
	private Random r = new Random();
	private int spawnTimer;
	private int levelTimer;
	private String[] side = { "left", "right", "top", "bottom" };
	ArrayList<Integer> levels = new ArrayList<Integer>(); // MAKE THIS AN ARRAY LIST SO I CAN REMOVE OBJECTS
	private int levelNumber = 0;
	private int tempCounter = 0;
	private Color trackerColor;
	private int trackerTimer;
	private int differentEnemies;

	public SpawnTest(Handler handler, HUD hud, Game game) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		handler.object.clear();
		hud.health = 100;
		hud.setScore(0);
		spawnTimer = 10;
		levelTimer = 150;
		tempCounter = 0;
		levelNumber = 1;
		trackerColor = Color.blue;
		trackerTimer = 300;
		differentEnemies = 5;
		addLevels();

	}

	/**
	 * Pre-load every level
	 */
	public void addLevels() {
		for (int i = 0; i <= differentEnemies; i++) {
			levels.add(i);
		}
	}

	/**
	 * Called once every 60 seconds by the Game loop
	 */
	public void tick() {
		hud.tick();
		if(levelNumber == 1){
			spawnTimer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				tempCounter++;
			}
			if(trackerTimer == 299){
				trackerColor = Color.blue;
			} else if (trackerTimer == 150){
				trackerColor = Color.black;
			} else if (trackerTimer == 0){
				trackerTimer = 300;
			}
			trackerTimer--;
			if(spawnTimer == 0){
				handler.addObject(
						new EnemyTracker(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), -5, ID.EnemyTracker, handler, trackerColor, trackerTimer, game));
				spawnTimer = 100;
			} 

			if (levelTimer == 0) {
				tempCounter = 0;
				handler.clearEnemies();
				spawnTimer = 10;
				trackerColor = Color.blue;
				trackerTimer = 300;
				levelNumber++;// set levelNumber to whatever index was randomly selected
			}
		} else if(levelNumber == 2){
			spawnTimer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				tempCounter++;
			}
			if (spawnTimer == 0) {
				handler.addObject(new EnemyExpand(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 100, 100, ID.EnemyExpand, this.handler));
				spawnTimer = 50;
			}
			if (levelTimer == 0) {
				tempCounter = 0;
				handler.clearEnemies();
				spawnTimer = 10;
				levelNumber++;// set levelNumber to whatever index was randomly selected
			}
		} else if (levelNumber == 3) {
			spawnTimer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				tempCounter++;
			}
			if(spawnTimer == 0) {
				handler.addObject(new EnemyMiniShooter(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 75, 75, -10, ID.EnemyMiniShooter, this.handler));
				handler.addObject(new EnemyMiniShooter(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 75, 75, -10, ID.EnemyMiniShooter, this.handler));
				handler.addObject(new EnemyMiniShooter(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 75, 75, -10, ID.EnemyMiniShooter, this.handler));
			}
			if (levelTimer == 0) {
				tempCounter = 0;
				handler.clearEnemies();
				spawnTimer = 10;
				levelNumber++;// set levelNumber to whatever index was randomly selected
			}
		} else if (levelNumber == 4) {
			spawnTimer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				tempCounter++;
			}
			if(spawnTimer == 0) {
				handler.addObject(new EnemyPorcupine(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 100, 100, ID.EnemyPorcupine, this.handler, -1, -2));
			}
			if (levelTimer == 0){
				tempCounter = 0;
				handler.clearEnemies();
				spawnTimer = 10;
				levelNumber++;// set levelNumber to whatever index was randomly selected
			}
		} else if(levelNumber == 5){
			spawnTimer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				tempCounter++;
			}
			if(spawnTimer == 0){
				handler.addPickup(
						new PickupHealth(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, ID.HealthPickup, "images/ham.png", this.handler));
				spawnTimer = 100;
			}
			if (levelTimer == 0) {
				tempCounter = 0;
				handler.clearEnemies();
				spawnTimer = 10;
				levelNumber++;// set levelNumber to whatever index was randomly selected
			}
		}
	}
	public void skipLevel() {
		spawnTimer = 10;
		tempCounter = 0;
		if(levelNumber < 5){
		levelNumber++;// set levelNumber to whatever index was randomly selected
		} else {
			levelNumber = 1;
		}
	}

	public void restart() {
		levelNumber = 1;
		spawnTimer = 10;
		tempCounter = 0;
		levelTimer = 150;
	}

}
