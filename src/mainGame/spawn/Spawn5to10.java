package mainGame.spawn;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import mainGame.Game.STATE;
import mainGame.enemy.*;
import mainGame.*;
import mainGame.gui.*;

/**
 * This class closely resembles Spawn1to10. Please refer to that class for
 * documentation
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Spawn5to10 {

	private Handler handler;
	private HUD hud;
	private Game game;
	private Random r = new Random();
	private int timer;
	private int levelTimer;
	private String[] side = { "left", "right", "top", "bottom" };
	ArrayList<Integer> levels = new ArrayList<Integer>();
	private int levelsLeft;
	private int levelNumber = 0;
	private int tempCounter = 0;
	public static int LEVEL_SET_2 = 0;
	private Color trackerColor;
	private int trackerTimer;
	private int differentEnemies;

	public Spawn5to10(Handler handler, HUD hud, Spawn1to5 spawner, Game game) {
		restart();
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		hud.restoreHealth();
		timer = 10;
		levelTimer = 150;
		levelsLeft = 5;
		tempCounter = 0;
		levelNumber = -1;
		trackerColor = Color.blue;
		trackerTimer = 1000;
		differentEnemies = 9;
		addLevels();
	}

	public void addLevels() {
		for (int i = 0; i <= differentEnemies; i++) {
			levels.add(i);
		}
	}

	public void tick() {
		// if(LEVEL_SET_2_RESET < 1) {
		// restart();
		// LEVEL_SET_2_RESET ++;
		// }
		if (levelNumber < 0) {
			levelTimer--;
			if (tempCounter < 1) {
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200, "Same levels...",
							ID.Levels1to10Text));
				handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2, "...but a little harder now",
							ID.Levels1to10Text));
				tempCounter++;
			}
			if (levelTimer <= 0) {
				handler.clearEnemies();
				tempCounter = 0;
				levelNumber = levels.get(this.rand());
			}

		}

		else if (levelNumber == 0) {
			timer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				tempCounter++;
			}
			if (timer == 0) {
				handler.addObject(
						new EnemyBasic(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 13, 13, ID.EnemyBasic, handler));
				timer = 80;
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				timer = 40;
				tempCounter = 0;
				if (levelsLeft == 1) {
					levelNumber = 101;
				} else {
					levelsLeft--;
					levelNumber = levels.get(this.rand());
				}
			}
		} else if (levelNumber == 1) {
			timer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				tempCounter++;
			}
			if (timer == 30) {
				handler.addObject(
						new EnemySweep(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 20, 2, ID.EnemySweep, handler));
			} else if (timer == 20) {
				handler.addObject(
						new EnemySweep(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 20, -2, ID.EnemySweep, handler));
			} else if (timer == 10) {
				handler.addObject(
						new EnemySweep(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 20, 4, ID.EnemySweep, handler));
			} else if (timer == 0) {
				handler.addObject(
						new EnemySweep(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), 20, -4, ID.EnemySweep, handler));
				timer = 45;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				tempCounter = 0;
				if (levelsLeft == 1) {
					levelNumber = 101;
				} else {
					levelsLeft--;
					levelNumber = levels.get(this.rand());
				}
			}
		} else if (levelNumber == 2) {
			timer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				tempCounter++;
			}
			if (timer == 0) {
				handler.addObject(
						new EnemySmart(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), -7, ID.EnemySmart, handler));
				timer = 60;
			}
			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				timer = 10;
				tempCounter = 0;
				if (levelsLeft == 1) {
					levelNumber = 101;
				} else {
					levelsLeft--;
					levelNumber = levels.get(this.rand());
				}
			}
		} else if (levelNumber == 3) {
			levelTimer--;
			if (tempCounter < 1) {
				handler.addObject(new EnemyShooter(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 100, 100,
							-30, ID.EnemyShooter, this.handler));
				levelTimer = 1200;
				tempCounter++;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				timer = 10;
				tempCounter = 0;
				if (levelsLeft == 1) {
					levelNumber = 101;
				} else {
					levelsLeft--;
					levelNumber = levels.get(this.rand());
				}
			}
		} else if (levelNumber == 4) {
			timer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				tempCounter++;
			}
			if (timer <= 0) {
				handler.addObject(new EnemyBurst(-250, 250, 75, 75, 250, side[r.nextInt(4)], ID.EnemyBurst, handler));
				timer = 120;
			}

			if (levelTimer == 0) {
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				timer = 10;
				tempCounter = 0;
				if (levelsLeft == 1) {
					levelNumber = 101;
				} else {
					levelsLeft--;
					levelNumber = levels.get(this.rand());
				}
			}
		} else if (levelNumber == 5) {
			timer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				tempCounter++;
			}
			if(trackerTimer == 999) {
				trackerColor = Color.blue;
			} else if (trackerTimer == 500) {
				trackerColor = Color.black;
			} else if (trackerTimer == 0) {
				trackerTimer = 1000;
			}
			trackerTimer--;
			if(timer == 0) {
				handler.addObject(
						new EnemyTracker(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), -5, ID.EnemyTracker, handler, trackerColor, trackerTimer, game));
				timer = 100;
			} 

			if (levelTimer == 0) {
				tempCounter = 0;
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				trackerColor = Color.blue;
				trackerTimer = 1000;
				timer = 10;
				if (levelsLeft == 1) {
					levelNumber = 101;
				} else {
					levelsLeft--;
					levelNumber = levels.get(this.rand());
				}
			}
		}
		else if (levelNumber == 6) {
			timer--;
			levelTimer--;
			if (tempCounter < 1) {
				tempCounter = 0;
				levelTimer = 1200;
				tempCounter++;
			}
			if (timer == 0) {
				handler.addObject(new EnemyExpand(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 100, 100, ID.EnemyExpand, this.handler));
				timer = 300;
				levelTimer = 1300;
			} 

			if (levelTimer == 0) {
				tempCounter = 0;
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				timer = 10;
				if (levelsLeft == 1) {
					levelNumber = 101;
				} else {
					levelsLeft--;
					levelNumber = levels.get(this.rand());
				}
			}
		}
		else if (levelNumber == 7) {
			timer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				tempCounter++;
			}
			if(timer == 0) {
				handler.addObject(new EnemyMiniShooter(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 75, 75, -15, ID.EnemyMiniShooter, this.handler, this.game));
				handler.addObject(new EnemyMiniShooter(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 75, 75, -15, ID.EnemyMiniShooter, this.handler, this.game));
				handler.addObject(new EnemyMiniShooter(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 75, 75, -15, ID.EnemyMiniShooter, this.handler, this.game));
			}
			if (levelTimer == 0) {
				tempCounter = 0;
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				if (levelsLeft == 1) {
					levelNumber = 101;
				} else {
					levelsLeft--;
					levelNumber = levels.get(this.rand());
				}
			}
		}
		else if (levelNumber == 8) {
			timer--;
			levelTimer--;
			if (tempCounter < 1) {
				levelTimer = 1200;
				tempCounter++;
			}
			if(timer == 0) {
				handler.addObject(new EnemyPorcupine(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 100, 100, ID.EnemyPorcupine, this.handler, -1, -2, this.game));
				handler.addObject(new EnemyPorcupine(r.nextInt(Game.WIDTH) - 35, r.nextInt(Game.HEIGHT) - 75, 100, 100, ID.EnemyPorcupine, this.handler, -1, -2, this.game));
			}
			if (levelTimer == 0) {
				tempCounter = 0;
				handler.clearEnemies();
				hud.setLevel(hud.getLevel() + 1);
				timer = 10;
				if (levelsLeft == 1) {
					levelNumber = 101;
				} else {// not time for the boss, just go to the next level
					levelsLeft--;
					levelNumber = levels.get(this.rand());// set levelNumber to whatever index was randomly selected
				}
			}
		}
		else if (levelNumber == 101) {
			if (tempCounter < 1) {
				hud.setLevel(101);
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
			} else if (tempCounter >= 1) {
				for (int i = 0; i < handler.object.size(); i++) {
					GameObject tempObject = handler.object.get(i);
					if (tempObject.getId() == ID.BossEye) {
						if (tempObject.getHealth() <= 0) {
							this.hud.setLevel(11);
							handler.clearEnemies();
							Spawn1to5.LEVEL_SET ++;
							game.gameState = STATE.Upgrade;
						}
					}
				}
			}

		}
		// WINNER
		// else if(levelNumber) {
		// levelTimer --;
		// if(tempCounter < 1) {
		// handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2 - 200,
		// "Same levels...", ID.Levels1to10Text));
		// handler.addObject(new LevelText(Game.WIDTH / 2 - 675, Game.HEIGHT / 2,
		// "...but a little harder now", ID.Levels1to10Text));
		// tempCounter++;
		// }
		// if(levelTimer <= 0) {
		// handler.clearEnemies();
		// tempCounter = 0;
		// levelNumber = levels.get(index);
		// }
		//
		// }

	}

	public void skipLevel() {
		if (levelsLeft == 1) {
			tempCounter = 0;
			levelNumber = 101;
		} else if (levelsLeft > 1) {
			timer = 10;
			levelsLeft--;
			tempCounter = 0;
			levelNumber = levels.get(this.rand());
		}
	}

	public void restart() {
		timer = 10;
		levelNumber = -10;
		tempCounter = 0;
		levelTimer = 150;
		levelsLeft = 10;

	}
	public int rand() {
		return (int) (Math.random() * differentEnemies);
	}

}
