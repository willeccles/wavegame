package mainGame;

import java.util.Random;

public class SpawnSurvival {

	private Handler handler;
	private HUD hud;
	private Game game;
	private int spawnTimer;
	private int differentEnemies;
	private Random r;
	private String[] side = {"left", "right", "top", "bottom"};

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
		differentEnemies = 5;	
	}

	public void tick() {
		hud.tick();
		// TODO Auto-generated method stub
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
			}
		}
		spawnTimer++;

	}

	public int randInt(){
		return (int) (Math.random()*(differentEnemies));
	}
}
