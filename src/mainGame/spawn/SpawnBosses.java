package mainGame.spawn;

import java.util.ArrayList;
import java.util.Random;

import mainGame.*;
import mainGame.enemy.*;
import mainGame.gui.*;

public class SpawnBosses {


	private Handler handler;
	private HUD hud;
	private Game game;
	public static int LEVEL_SET = 1;
	private int differentEnemies;
	private Random r;
	private int levelNumber = -1;
	private int tempCounter = 0;
	ArrayList<Integer> levels = new ArrayList<Integer>(); // MAKE THIS AN ARRAY LIST SO I CAN REMOVE OBJECTS
	private Player player;
	private int levelTimer;

	public SpawnBosses(Handler handler, HUD hud, Game game, Player player) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		handler.object.clear();
		hud.health = 100;
		hud.setScore(0);
		hud.setLevel(1);
		r = new Random();
		//different types of enemies added
		differentEnemies = 4;
		this.player = player;
		levelTimer = 150;
	}

	public void tick() {
		if (levelNumber < 0) {
			levelTimer--;
			if (tempCounter < 1) {// display intro game message ONE time
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, "Are you ready to RUMBLE!",
						ID.Levels1to10Text));
				tempCounter++;
			}
			if (levelTimer <= 0) {// time to play!
				handler.clearEnemies();
				tempCounter = 0;
				levelNumber = this.randLevel();
			}
		}

		else if (levelNumber == 0) {// this is level 1
			if (tempCounter < 1) {
				handler.addObject(new EnemyBoss(ID.EnemyBoss, handler));
				tempCounter++;
			} else if (tempCounter >= 1) {
				for (int i = 0; i < handler.object.size(); i++) {
					GameObject tempObject = handler.object.get(i);
					if (tempObject.getId() == ID.EnemyBoss) {
						if (tempObject.getHealth() <= 0) {
							handler.clearEnemies();
							levelNumber = this.randLevel();
							tempCounter = 0;
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
							levelNumber = this.randLevel();
							tempCounter = 0;
							hud.setLevel(hud.getLevel()+1);
						}
					}
				}
			}
		}
		else if (levelNumber == 2) {
			if (tempCounter < 1) {
				tempCounter++;
				handler.addObject(new RollBoss1(r.nextInt(Game.WIDTH-300), r.nextInt(Game.HEIGHT-300), 11, 11, ID.RollBoss1, handler));
				handler.addObject(new RollBoss2(r.nextInt(Game.WIDTH-300), r.nextInt(Game.HEIGHT-300), 11, 11, ID.RollBoss2, handler));
			} else if (tempCounter >= 1) {
				for (int i = 0; i < handler.object.size(); i++) {
					GameObject tempObject = handler.object.get(i);
					if (tempObject.getId() == ID.RollBoss1) {
						if (tempObject.getHealth() <= 0) {
							handler.clearEnemies();
							levelNumber = this.randLevel();
							tempCounter = 0;
							hud.setLevel(hud.getLevel()+1);
						}
					}
				}
			}
		}
		else if (levelNumber == 3) {
			if(tempCounter == 0) {
				handler.addObject(new BossSeparates(r.nextInt(Game.WIDTH-400), r.nextInt(Game.HEIGHT-400), ID.SeparateBoss, handler, player, 400, 2000, -4, -4));
				tempCounter++;
			} else if (tempCounter == 1) {
				for (int i = 0; i < handler.object.size(); i++) {
					GameObject tempObject = handler.object.get(i);
					if (tempObject.getId() == ID.SeparateBoss) {
						if (tempObject.getHealth() == 1000) {
							double x = tempObject.getX();
							double y = tempObject.getY();
							handler.removeObject(tempObject);
							handler.addObject(new BossSeparates(x, y, ID.SeparateBoss2, handler, player, 200, 1000, -3, -5));
							handler.addObject(new BossSeparates(x, y+200, ID.SeparateBoss2, handler, player, 200, 1000, -4, 3));
							handler.addObject(new BossSeparates(x+200, y+200, ID.SeparateBoss2, handler, player, 200, 1000, 3, 4));
							handler.addObject(new BossSeparates(x+200, y, ID.SeparateBoss2, handler, player, 200, 1000, 3, -3));
							tempCounter++;
						} 
					}
				}
			} else if(tempCounter < 6) {
				for (int i = 0; i < handler.object.size(); i++) {
					GameObject tempObject = handler.object.get(i);
					if (tempObject.getId() == ID.SeparateBoss2) {
						if (tempObject.getHealth() < 500) {
							double x = tempObject.getX();
							double y = tempObject.getY();
							handler.removeObject(tempObject);
							handler.addObject(new BossSeparates(x, y, ID.SeparateBoss3, handler, player, 100, 500, -3, -5));
							handler.addObject(new BossSeparates(x, y+100, ID.SeparateBoss3, handler, player, 100, 500, -4, 3));
							handler.addObject(new BossSeparates(x+100, y+100, ID.SeparateBoss3, handler, player, 100, 500, 3, 4));
							handler.addObject(new BossSeparates(x+100, y, ID.SeparateBoss3, handler, player, 100, 500, 3, -3));
							tempCounter++;
							break;
						} 
					}
				}
			} else if (tempCounter >= 6) {
				for (int i = 0; i < handler.object.size(); i++) {
					GameObject tempObject = handler.object.get(i);
					if (tempObject.getId() == ID.SeparateBoss3) {
						if (tempObject.getHealth() <= 0) {
							handler.clearEnemies();
							levelNumber = this.randLevel();
							tempCounter = 0;
							hud.setLevel(hud.getLevel()+1);
						}
					}
				}
			}
		}
	}


	public void restart() {
		handler.object.clear();
		hud.health = 100;
		hud.setScore(0);
		hud.setLevel(1);
		levelNumber = this.randLevel();
		tempCounter = 0;
		levelTimer = 150;
		player.resetLoc();
		player.resetVel();
	}

	public int randLevel() {
		return (int) (Math.random()*(differentEnemies));
	}

}

