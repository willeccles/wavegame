package mainGame;

import java.awt.Color;
import java.util.Random;

/**
 * A type of game mode in the game
 * 
 * @author Kyle Gorman 10/16/17
 *
 */

public class SpawnSurvival {

	private Handler handler;
	private HUD hud;
	private Game game;
	private int spawnTimer;
	private int differentEntities;
	private Random r;
	private String[] side = {"left", "right", "top", "bottom"};
	private int trackerTimer;
	private Color trackerColor;
	private int count;
	private int spawnNum;
	private int tempCounter;
	private boolean textThere;

	public SpawnSurvival(Handler handler, HUD hud, Game game){
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
		differentEntities = 11;	
		trackerTimer = 1000;
		trackerColor = Color.blue;
		count = 0;
		spawnNum = -1;
		tempCounter = 0;
		textThere = false;
	}

	public void tick() {
		tempCounter++;
		// updates the trackers color
		if(trackerTimer == 999){
			trackerColor = Color.blue;
		} else if (trackerTimer == 500){
			trackerColor = Color.black;
		} else if (trackerTimer == 0){
			trackerTimer = 1000;
		}
		//prevents the trackers from spawning invisible 
		if(count == 1){
			trackerTimer--;
		}
		if(spawnNum == -1){
			if(!textThere){
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, "Good luck!",
						ID.SurvivalText));
				textThere = true;
			}
			if(tempCounter > 120){
				handler.clearEnemies();
				tempCounter = 0;
			}
		}
		if(spawnTimer == 120){
			spawnNum = randInt();
			//spawnNum = 9;
			if(spawnNum == 0){
				//spawns Basic enemy

				handler.addObject(
						new EnemyBasic(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 9, 9, ID.EnemyBasic, handler));
				spawnTimer = 0;

			} else if(spawnNum == 1){
				//spawns Burst enemy

				handler.addObject(
						new EnemyBurst(-200, 200, 50, 50, 200, side[r.nextInt(4)], ID.EnemyBurst, handler));
				spawnTimer = 0;

			} else if(spawnNum == 2){
				//spawns Sweep enemy

				int sweepTemp = (int) (Math.random()*4);
				if (sweepTemp == 0) {
					handler.addObject(
							new EnemySweep(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 15, 1, ID.EnemySweep, handler));
				} else if (sweepTemp == 1) {
					handler.addObject(
							new EnemySweep(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 15, -1, ID.EnemySweep, handler));
				} else if (sweepTemp == 2) {
					handler.addObject(
							new EnemySweep(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 15, 3, ID.EnemySweep, handler));
				} else if (sweepTemp == 3) {
					handler.addObject(
							new EnemySweep(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 15, -3, ID.EnemySweep, handler));
				}
				spawnTimer = 0;

			} else if(spawnNum == 3){
				//spawns Smart enemy

				handler.addObject(
						new EnemySmart(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), -5, ID.EnemySmart, handler));
				spawnTimer = 0;

			} else if(spawnNum == 4){
				//spawns Shooter enemy

				handler.addObject(
						new EnemyShooter(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 100, 100,-20, ID.EnemyShooter, this.handler));
				spawnTimer = 0;
			} else if(spawnNum == 5){
				//spawns Tracker enemy

				count = 1;
				handler.addObject(
						new EnemyTracker(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), -5, ID.EnemyTracker, handler, trackerColor, trackerTimer, game));
				spawnTimer = 0;
			} else if (spawnNum == 6){
				//spawns Expansion enemy

				handler.addObject(new EnemyExpand(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 100, 100, ID.EnemyExpand, this.handler));
				spawnTimer = 0;
			} else if (spawnNum == 7){
				//spawns Minishooter enemy

				handler.addObject(new EnemyMiniShooter(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 75, 75, -10, ID.EnemyMiniShooter, this.handler));
				handler.addObject(new EnemyMiniShooter(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 75, 75, -10, ID.EnemyMiniShooter, this.handler));
				spawnTimer = 0;
			} else if (spawnNum == 8){
				//spawns Porcupine enemy

				handler.addObject(new EnemyPorcupine(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 100, 100, ID.EnemyPorcupine, this.handler, -1, -2));
				spawnTimer = 0;
			} else if (spawnNum == 9){
				//spawns Health pickup

				handler.addPickup(new PickupHealth(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, ID.HealthPickup, "images/ham.png", this.handler));
				spawnTimer = 0;
			} else if (spawnNum == 10){
				//spawns Health pickup

				handler.addPickup(new PickupSpeed(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, ID.SpeedPickup, "images/ham.png", this.handler));
				spawnTimer = 0;
			}
		}
		spawnTimer++;

	}

	public int randInt(){
		return (int) (Math.random()*(differentEntities));
	}

	public void restart(){
		spawnTimer = 0;
	}
}
