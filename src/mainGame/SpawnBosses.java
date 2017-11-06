package mainGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import mainGame.Game.STATE;

public class SpawnBosses {


	private Handler handler;
	private HUD hud;
	private Game game;
	public static int LEVEL_SET = 1;
	private int spawnTimer;
	private int differentEnemies;
	private Random r;
	private int levelsRemaining;
	private int levelNumber = 3;
	private int tempCounter = 0;
	private int levelTimer;
	ArrayList<Integer> levels = new ArrayList<Integer>(); // MAKE THIS AN ARRAY LIST SO I CAN REMOVE OBJECTS
	private int count;
	private Player player;
	
	public SpawnBosses(Handler handler, HUD hud, Game game, Player player) {
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
		differentEnemies = 4;
		count = 0;
		this.player = player;
	}

	public void tick() {
		if (levelNumber < 0) {

		}

		else if (levelNumber == 0) {// this is level 1
			if (tempCounter < 1) {
				handler.addObject(new BullBoss(ID.BullBoss, handler));
				tempCounter++;
			} else if (tempCounter >= 1) {
				for (int i = 0; i < handler.object.size(); i++) {
					GameObject tempObject = handler.object.get(i);
					if (tempObject.getId() == ID.BullBoss) {
						if (tempObject.getHealth() <= 0) {
							handler.removeObject(tempObject);
							//LEVEL_SET++;
							levelNumber++;

						}
					}
				}
			}
		}
		else if (levelNumber == 1) {
			if (tempCounter < 1) {
				handler.addObject(new BossEye(Game.WIDTH / 2 - 150, Game.HEIGHT / 2 - 150, ID.BossEye, handler, 1));
				handler.addObject(new BossEye(Game.WIDTH / 2 - 50, Game.HEIGHT / 2 - 150, ID.BossEye, handler, 2));
				handler.addObject(new BossEye(Game.WIDTH / 2 + 50, Game.HEIGHT / 2 - 150, ID.BossEye, handler, 3));
				handler.addObject(new BossEye(Game.WIDTH / 2 - 150, Game.HEIGHT / 2 - 50, ID.BossEye, handler, 4));
				handler.addObject(new BossEye(Game.WIDTH / 2 - 50, Game.HEIGHT / 2 - 50, ID.BossEye, handler, 5));
				handler.addObject(new BossEye(Game.WIDTH / 2 + 50, Game.HEIGHT / 2 - 50, ID.BossEye, handler, 6));
				handler.addObject(new BossEye(Game.WIDTH / 2 - 150, Game.HEIGHT / 2 + 50, ID.BossEye, handler, 7));
				handler.addObject(new BossEye(Game.WIDTH / 2 - 50, Game.HEIGHT / 2 + 50, ID.BossEye, handler, 8));
				handler.addObject(new BossEye(Game.WIDTH / 2 + 50, Game.HEIGHT / 2 + 50, ID.BossEye, handler, 9));
				tempCounter++;
			}  else if (tempCounter >= 1) {
				for (int i = 0; i < handler.object.size(); i++) {
					GameObject tempObject = handler.object.get(i);
					if (tempObject.getId() == ID.BossEye) {
						if (tempObject.getHealth() <= 0) {
							handler.clearEnemies();
							levelNumber++;
						}
					}
				}
			}
		}
		else if (levelNumber == 2) {
			if (tempCounter < 1) {
				tempCounter++;
				handler.addObject(new RollBoss1(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 11, 11, ID.RollBoss1, handler));
				handler.addObject(new RollBoss2(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 11, 11, ID.RollBoss2, handler));
			}
		}
		else if (levelNumber == 3) {
			if(tempCounter < 1) {
				tempCounter++;
				handler.addObject(new BossKyle(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.BossKyle, handler, player));
			}
		}
	}

	public void restart() {
		spawnTimer = 10;
		tempCounter = 0;
		levelTimer = 150;
		levelsRemaining = 5;
	}

	public int randInt() {
		return (int) (Math.random()*(differentEnemies));
	}

}

