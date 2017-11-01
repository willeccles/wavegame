package mainGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import mainGame.Game.STATE;
import mainGame.audio.SoundPlayer;

/**
 * Handles key input from the user
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class KeyInput extends KeyAdapter {

	private Handler handler;
	private boolean[] keyDown = new boolean[5];
	private int speed;
	private Game game;
	private HUD hud;
	private Player player;
	private Spawn1to5 spawner;
	private Upgrades upgrades;
	private String ability;
	private Leaderboard leaderboard;
	private Object object;
	private Image pauseMenu;
	private SoundPlayer soundplayer;

	// uses current handler created in Game as parameter
	public KeyInput(Handler handler, Game game, HUD hud, Player player, Spawn1to5 spawner, Upgrades upgrades,
			Leaderboard leaderboard) {
		this.handler = handler;
		this.speed = Player.playerSpeed;
		this.game = game;
		this.player = player;
		this.hud = hud;
		this.spawner = spawner;
		this.upgrades = upgrades;
		this.leaderboard = leaderboard;

		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
		keyDown[3] = false;
		keyDown[4] = false;

	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		this.speed = Player.playerSpeed;

		// finds what key strokes associate with Player
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			// using only if's allows multiple keys to be triggered at once
			// if (tempObject.getId() == ID.Player) {
			// find the player object, as he is the only one the
			// user can control key events for player 1
			if (key == KeyEvent.VK_UP && handler.getTimer() <= 0) {
				player.setVelY(-(this.speed));
				keyDown[0] = true;
				game.updatePlayerPosition();
			}
			if (key == KeyEvent.VK_LEFT && handler.getTimer() <= 0) {
				player.setVelX(-(this.speed));
				keyDown[1] = true;
				game.updatePlayerPosition();
			}
			if (key == KeyEvent.VK_DOWN && handler.getTimer() <= 0) {
				player.setVelY(this.speed);
				keyDown[2] = true;
				game.updatePlayerPosition();
			}
			if (key == KeyEvent.VK_RIGHT && handler.getTimer() <= 0) {
				player.setVelX(this.speed);
				keyDown[3] = true;
				game.updatePlayerPosition();
			}
			if (key == KeyEvent.VK_SPACE && handler.getTimer() <= 0) {
				upgrades.levelSkipAbility();
			}
			if (key == KeyEvent.VK_SHIFT && handler.getTimer() <= 0) {
				ability = upgrades.getAbility();
				if (ability.equals("clearScreen")) {
					upgrades.clearScreenAbility();
				} else if (ability.equals("levelSkip")) {
					upgrades.levelSkipAbility();
				} else if (ability.equals("freezeTime")) {
					upgrades.freezeTimeAbility();
				} else if (ability.equals("")) {

				}
			}

		}
		if (key == KeyEvent.VK_ESCAPE) {
			if (game.gameState != STATE.Menu) {
				if (game.isPaused() == true) {
					game.gameState = game.getCurrentGame();
					game.unPause();

				} else {
					game.gameState = STATE.PauseMenu;
					game.pause();
				}
			}
		}

		if (key == KeyEvent.VK_M) {
			game.musicKeyPressed();
		}

		if (game.getGameState() == STATE.Leaderboard) {
			if (key == KeyEvent.VK_A)
				System.out.println("test");
			if (leaderboard.getFull()) {
				if (key == KeyEvent.VK_BACK_SPACE) {
					if (leaderboard.getLoc() >= 0) {
						leaderboard.updateUser("back");
						leaderboard.updateLoc(-1);
					}
				}

			} else {
				if (key == KeyEvent.VK_A) {
					leaderboard.updateUser("a");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_B) {
					leaderboard.updateUser("b");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_C) {
					leaderboard.updateUser("c");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_D) {
					leaderboard.updateUser("d");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_E) {
					leaderboard.updateUser("e");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_F) {
					leaderboard.updateUser("f");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_G) {
					leaderboard.updateUser("g");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_H) {
					leaderboard.updateUser("h");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_I) {
					leaderboard.updateUser("i");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_J) {
					leaderboard.updateUser("j");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_K) {
					leaderboard.updateUser("k");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_L) {
					leaderboard.updateUser("l");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_M) {
					leaderboard.updateUser("m");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_N) {
					leaderboard.updateUser("n");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_O) {
					leaderboard.updateUser("o");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_P) {
					leaderboard.updateUser("p");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_Q) {
					leaderboard.updateUser("q");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_R) {
					leaderboard.updateUser("r");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_S) {
					leaderboard.updateUser("s");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_T) {
					leaderboard.updateUser("t");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_U) {
					leaderboard.updateUser("u");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_V) {
					leaderboard.updateUser("v");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_W) {
					leaderboard.updateUser("w");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_X) {
					leaderboard.updateUser("x");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_Y) {
					leaderboard.updateUser("y");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_Z) {
					leaderboard.updateUser("z");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_1) {
					leaderboard.updateUser("1");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_2) {
					leaderboard.updateUser("2");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_3) {
					leaderboard.updateUser("3");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_4) {
					leaderboard.updateUser("4");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_5) {
					leaderboard.updateUser("5");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_6) {
					leaderboard.updateUser("6");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_7) {
					leaderboard.updateUser("7");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_8) {
					leaderboard.updateUser("8");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_9) {
					leaderboard.updateUser("9");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_0) {
					leaderboard.updateUser("0");
					leaderboard.updateLoc(1);
				} else if (key == KeyEvent.VK_BACK_SPACE) {
					if (leaderboard.getLoc() >= 0) {
						leaderboard.updateUser("back");
						leaderboard.updateLoc(-1);

					}
				}

			}
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.Player) {
				// key events for player 1
				if (key == KeyEvent.VK_UP) {
					keyDown[0] = false;
				} else if (key == KeyEvent.VK_LEFT) {
					keyDown[1] = false;
				} else if (key == KeyEvent.VK_DOWN) {
					keyDown[2] = false;
				} else if (key == KeyEvent.VK_RIGHT) {
					keyDown[3] = false;
					keyDown[4] = false;
				}

				// vertical movement
				if (!keyDown[0] && !keyDown[2]) {
					tempObject.setVelY(0);
				}
				// horizontal movement
				if (!keyDown[1] && !keyDown[3]) {
					tempObject.setVelX(0);
				}
				game.updatePlayerPosition();
			}

		}

		// if (key == KeyEvent.VK_ESCAPE) System.exit(1);
	}

}
