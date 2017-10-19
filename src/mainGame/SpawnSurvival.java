package mainGame;

import java.awt.Color;
import java.util.Random;

public class SpawnSurvival {

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
		differentEnemies = 8;	
		trackerTimer = 1000;
		trackerColor = Color.blue;
		count = 0;
	}

	public void tick() {
		hud.tick();
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
		int temp = randInt();
		//System.out.println(spawnTimer);
		if(spawnTimer == 100){
			//spawns Basic enemy
			if(temp == 0){
				handler.addObject(
						new EnemyBasic(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 9, 9, ID.EnemyBasic, handler));
				spawnTimer = 0;

			} else if(temp == 1){
				//spawns Burst enemy

				handler.addObject(
						new EnemyBurst(-200, 200, 50, 50, 200, side[r.nextInt(4)], ID.EnemyBurst, handler));
				spawnTimer = 0;

			} else if(temp == 2){
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

			} else if(temp == 3){
				//spawns Smart enemy

				handler.addObject(
						new EnemySmart(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), -5, ID.EnemySmart, handler));
				spawnTimer = 0;

			} else if(temp == 4){
				//spawns Shooter enemy

				handler.addObject(
						new EnemyShooter(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 100, 100,-20, ID.EnemyShooter, this.handler));
				spawnTimer = 0;
			} else if(temp == 5){
				//spawns Tracker enemy
				count = 1;
				handler.addObject(
						new EnemyTracker(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), -5, ID.EnemyTracker, handler, trackerColor, trackerTimer));
				spawnTimer = 0;
			} else if (temp == 6){
				//spawns Expansion enemy
				handler.addObject(new EnemyExpand(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 100, 100, ID.EnemyExpand, this.handler));
				spawnTimer = 0;
			} else if (temp == 7){
				handler.addObject(new EnemyMiniShooter(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 75, 75, -10, ID.EnemyMiniShooter, this.handler));
				handler.addObject(new EnemyMiniShooter(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 75, 75, -10, ID.EnemyMiniShooter, this.handler));
				spawnTimer = 0;
			}
		}
		spawnTimer++;

	}

	public int randInt(){
		return (int) (Math.random()*(differentEnemies));
	}
	
	public void restart(){
		spawnTimer = 0;
	}
}
