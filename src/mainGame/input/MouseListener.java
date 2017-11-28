package mainGame.input;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import mainGame.Game.STATE;
import mainGame.spawn.*;
import mainGame.*;
import mainGame.gui.*;


public class MouseListener extends MouseAdapter {

	private Game game;
	private Handler handler;
	private HUD hud;
	private Spawn1to5 spawner;
	private Spawn5to10 spawner2;
	private SpawnSurvival spawnSurvival;
	private SpawnBosses spawnBosses;
	private SpawnMultiplayer spawnMulti;
	private UpgradeScreen upgradeScreen;
	private Upgrades upgrades;
	private Player player;
	private Leaderboard leaderboard;
	private LeaderboardDisplay leaderboardDisplay;

	public MouseListener(Game game, Handler handler, HUD hud, Spawn1to5 spawner, 
			Spawn5to10 spawner2, SpawnSurvival spawnSurvival, UpgradeScreen upgradeScreen, 
			SpawnMultiplayer spawnM, Player player, Upgrades upgrades, 
			Leaderboard leaderboard, SpawnBosses spawnBosses, 
			LeaderboardDisplay leaderboardDisplay) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.spawner = spawner;
		this.spawner2 = spawner2;
		this.upgradeScreen = upgradeScreen;
		this.player = player;
		this.upgrades = upgrades;
		this.spawnSurvival = spawnSurvival;
		this.spawnMulti = spawnM;
		this.leaderboard = leaderboard;
		this.spawnBosses = spawnBosses;
		this.leaderboardDisplay = leaderboardDisplay;
		
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if (!game.isPaused()) { // game is not paused
			if (game.gameState == STATE.GameOver) {
				if (player.checkGame() == "waves") {
					upgrades.resetUpgrades();
					spawner.restart();
					spawner.addLevels();
					spawner2.restart();
					spawner2.addLevels();
					Spawn1to5.LEVEL_SET = 1;
					game.gameState = STATE.Menu;
				} else if (player.checkGame() == "survival") {
					spawnSurvival.restart();
					game.gameState = STATE.Leaderboard;
				} else if (player.checkGame() == "bosses") {
					spawnBosses.restart();
					game.gameState = STATE.Menu;
				} else if (player.checkGame() == "multiplayer") {
					spawnMulti.reset();
					game.gameState = STATE.Menu;
				}
			}

			else if (game.gameState == STATE.Wave) {
			}
			else if (game.gameState == STATE.Multiplayer) {
			}
			else if (game.gameState == STATE.Bosses) {			
			}
			else if (game.gameState == STATE.Survival) {
			}

			else if (game.gameState == STATE.Upgrade) {
				if (mouseOver(mx, my, 210, 210, 860, 150)) {
					upgrades.activateUpgrade(upgradeScreen.getPath(1));
					upgradeScreen.removeUpgradeOption(1);
				} else if (mouseOver(mx, my, 210, 200 + 150, 860, 150)) {
					upgrades.activateUpgrade(upgradeScreen.getPath(2));
					upgradeScreen.removeUpgradeOption(2);
				} else if (mouseOver(mx, my, 100, 200 + 2 * 150, 860, 150)) {
					upgrades.activateUpgrade(upgradeScreen.getPath(3));
					upgradeScreen.removeUpgradeOption(3);
				}
				game.gameState = STATE.Wave;
			}

			else if (game.gameState == STATE.Menu) {
				// Waves Button
				if (mouseOver(mx, my, 30, 90, 1220, 120)) {
					handler.object.clear();
					game.gameState = STATE.Wave;
					handler.addObject(player);
					// handler.addPickup(new PickupHealth(100, 100, ID.PickupHealth,
					// "images/PickupHealth.png", handler));
				}
			
				//Multiplayer Join Button
				else if (mouseOver(mx, my, 30, 240, 600, 120)) {
					game.gameState = STATE.Join;
				}
				//Multiplayer Host Button
				else if (mouseOver(mx, my, 640, 240, 610, 120)) {
					game.gameState = STATE.Host;
				}

				// Help Button
				else if (mouseOver(mx, my, 440, 600, 380, 90)) {
					game.gameState = STATE.Help;
				}


				// LeaderBoards Button
				 else if (mouseOver(mx, my, 40, 600, 380, 90)) {
					 
				} 
				// Quit Button
				else if (mouseOver(mx, my, 840, 600, 390, 90)) {
					System.exit(1);
				}

				// Color Picker Mode
				else if (mouseOver(mx, my, 555, 395, 175, 175)) {
					handler.object.clear();
					game.gameState = STATE.Color;
					handler.addObject(player);
					//game.gameState = STATE.Connect;
					// switch to the multiplayer connection screen, see Game::tick()
					// the player gets added inside of SpawnMultiplayer at the same time as the opponent
				}

				// Survival Mode
				else if (mouseOver(mx, my, 760, 390, 450, 180)) {
					hud.setScore(0);
					handler.object.clear();
					game.gameState = STATE.Survival;
					handler.addObject(player);
				}

				// Bosses Mode
				else if (mouseOver(mx, my, 70, 390, 450, 180)) {
					handler.object.clear();
					game.gameState = STATE.Bosses;
					handler.addObject(player);
				}
			}
			// Back Button for Help screen
			else if (game.gameState == STATE.Help) {
				if (mouseOver(mx, my, 566, 590, 133, 42)) {
					game.gameState = STATE.Menu;
					return;
				}
			}
			// Leaderboard screen
			else if (game.gameState == STATE.Leaderboard) {
				if (mouseOver(mx, my, 353, 490, 566, 166)) {
					leaderboard.loadLeaderboard();
					leaderboardDisplay.refresh();
					game.gameState = STATE.LeaderboardDisplay;
					return;
				}
			} else if (game.gameState == STATE.Help) {
				if (mouseOver(mx, my, 566, 560, 133, 42)) {
					game.gameState = STATE.Menu;
					return;
				}
			} else if(game.gameState == STATE.Color) {
				int x = 0;
				int y = 0;
				if(mouseOver(mx, my,x+70, y+75, 125, 125)) {
					player.updateColors(Color.white, null);
				} else if(mouseOver(mx, my, x+320, y+75, 125, 125)) {
					player.updateColors(Color.blue, null);
				} else if(mouseOver(mx, my, x+570, y+75, 125, 125)) {
					player.updateColors(Color.yellow, null);
				} else if(mouseOver(mx, my,x+820, y+75, 125, 125)) {
					player.updateColors(Color.cyan, null);
				} else if(mouseOver(mx, my,x+1070, y+75, 125, 125)) {
					player.updateColors(Color.gray, null);
				} else if(mouseOver(mx, my,x+70, y+215, 125, 125)) {
					player.updateColors(Color.green, null);
				} else if(mouseOver(mx, my,x+320, y+215, 125, 125)) {
					player.updateColors(Color.magenta, null);
				} else if(mouseOver(mx, my,x+570, y+215, 125, 125)) {
					player.updateColors(Color.orange, null);
				} else if(mouseOver(mx, my,x+820, y+215, 125, 125)) {
					player.updateColors(Color.pink, null);
				} else if(mouseOver(mx, my,x+1070, y+215, 125, 125)) {
					player.updateColors(Color.red, null);
				} else if(mouseOver(mx, my,x+70, y+440, 125, 125)) {
					player.updateColors(null, Color.white);
				} else if(mouseOver(mx, my, x+320, y+440, 125, 125)) {
					player.updateColors(null, Color.blue);
				} else if(mouseOver(mx, my, x+570, y+440, 125, 125)) {
					player.updateColors(null, Color.yellow);
				} else if(mouseOver(mx, my,x+820, y+440, 125, 125)) {
					player.updateColors(null, Color.cyan);
				} else if(mouseOver(mx, my,x+1070, y+440, 125, 125)) {
					player.updateColors(null, Color.gray);
				} else if(mouseOver(mx, my,x+70, y+585, 125, 125)) {
					player.updateColors(null, Color.green);
				} else if(mouseOver(mx, my,x+320, y+585, 125, 125)) {
					player.updateColors(null, Color.magenta);
				} else if(mouseOver(mx, my,x+570, y+585, 125, 125)) {
					player.updateColors(null, Color.orange);
				} else if(mouseOver(mx, my,x+820, y+585, 125, 125)) {
					player.updateColors(null, Color.pink);
				} else if(mouseOver(mx, my,x+1070, y+585, 125, 125)) {
					player.updateColors(null, Color.red);
				} else if (mouseOver(mx, my,1000, 10, 250, 60)){
					game.gameState = STATE.Menu;
					handler.clearPlayer();
				}
			} else if(game.gameState == STATE.LeaderboardDisplay) {
				if(mouseOver(mx,my,0,0,Game.WIDTH,Game.HEIGHT)) {
					leaderboard.reset();
					game.gameState = STATE.Menu;
				}
			}
		} else { // game is paused
			// PauseMenu-> Resume
			if (mouseOver(mx, my, 445, 37, 390, 329)) {
				game.unPause();
				return;
				//PauseMenu-> Main Menu
			} if (mouseOver(mx, my, 445, 372, 390, 337)) {
				// TODO: make one method in the handler for resetGame() that does all of the following things
				game.unPause();
				game.gameState = STATE.Menu;
				handler.clearEnemies();
				handler.clearPlayer();
				handler.pickups.clear();
				hud.setScore(0);
				hud.updateScoreColor(Color.white);
				hud.setHealth(100);
				hud.setLevel(1);
				hud.setExtraLives(0);
				upgrades.resetUpgrades();
				spawner.restart();
				spawner.addLevels();
				spawner2.restart();
				spawner2.addLevels();
				Spawn1to5.LEVEL_SET = 1;
				return;
			}
		}

	}

	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * Helper method to detect is the mouse is over a "button" drawn via
	 * Graphics
	 * @param mx
	 *            mouse x position
	 * @param my
	 *            mouse y position
	 * @param x
	 *            button x position
	 * @param y
	 *            button y position
	 * @param width
	 *            button width
	 * @param height
	 *            button height
	 * @return boolean, true if the mouse is contained within the button
	 */
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			} else
				return false;
		} else
			return false;
	}
}
