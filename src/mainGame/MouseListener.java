package mainGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import mainGame.Game.STATE;

/**
 * Handles all mouse input
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class MouseListener extends MouseAdapter {

	private Game game;
	private Handler handler;
	private HUD hud;
	private Spawn1to5 spawner;
	private Spawn5to10 spawner2;
	private UpgradeScreen upgradeScreen;
	private Upgrades upgrades;
	private Player player;
	private String upgradeText;

	public MouseListener(Game game, Handler handler, HUD hud, Spawn1to5 spawner, Spawn5to10 spawner2,
			UpgradeScreen upgradeScreen, Player player, Upgrades upgrades) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.spawner = spawner;
		this.spawner2 = spawner2;
		this.upgradeScreen = upgradeScreen;
		this.player = player;
		this.upgrades = upgrades;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (game.gameState == STATE.GameOver) {
			handler.object.clear();
			upgrades.resetUpgrades();
			hud.health = 100;
			hud.setScore(0);
			hud.setLevel(1);
			spawner.restart();
			spawner.addLevels();
			spawner2.restart();
			spawner2.addLevels();
			Spawn1to5.LEVEL_SET = 1;
			game.gameState = STATE.Menu;
		}

		else if (game.gameState == STATE.Game) {

		}

		else if (game.gameState == STATE.Upgrade) {
			if (mouseOver(mx, my, 100, 300, 1721, 174)) {
				upgradeText = upgradeScreen.getPath(1);

				upgrades.activateUpgrade(upgradeText);

				upgradeScreen.removeUpgradeOption(1);

				game.gameState = STATE.Game;
			} else if (mouseOver(mx, my, 100, 300 + (60 + Game.HEIGHT / 6), 1721, 174)) {
				upgradeText = upgradeScreen.getPath(2);

				upgrades.activateUpgrade(upgradeText);

				upgradeScreen.removeUpgradeOption(2);

				game.gameState = STATE.Game;
			} else if (mouseOver(mx, my, 100, 300 + 2 * (60 + Game.HEIGHT / 6), 1721, 174)) {
				upgradeText = upgradeScreen.getPath(3);

				upgrades.activateUpgrade(upgradeText);

				upgradeScreen.removeUpgradeOption(3);

				game.gameState = STATE.Game;
			}

		}

		else if (game.gameState == STATE.Menu) {
			// Waves Button
			if (mouseOver(mx, my, 660, 90, 266, 266)) {
				handler.object.clear();
				game.gameState = STATE.Game;
				handler.addObject(player);
				// handler.addPickup(new PickupHealth(100, 100, ID.PickupHealth,
				// "images/PickupHealth.png", handler));
			}

			// Help Button
			else if (mouseOver(mx, my, 53, 90, 566, 166)) {
				game.gameState = STATE.Help;
			}

			// Credits
			else if (mouseOver(mx, my, 53, 290, 566, 166)) {
				JOptionPane.showMessageDialog(game,
						"Made by Brandon Loehle for his "
								+ "final project in AP Computer Science senior year, 2015 - 2016."
								+ "\n\nThis game is grossly unfinished with minor bugs. However,"
								+ " it is 100% playable, enjoy!");
			}

			// Quit Button
			else if (mouseOver(mx, my, 53, 490, 566, 166)) {
				System.exit(1);
			}

			//Attack Mode
			else if (mouseOver(mx, my, 660, 390, 266, 266)) {
				game.gameState = STATE.Attack;
				handler.addObject(player);
				handler.object.clear();
			}

			//Survival Mode
			else if (mouseOver(mx, my, 960, 390, 266, 266)) {
				game.gameState = STATE.Survival;
				handler.addObject(player);
				handler.object.clear();
			}

			//Bosses Mode
			else if (mouseOver(mx, my, 960, 90, 266, 266)) {
				game.gameState = STATE.Bosses;
				handler.addObject(player);
				handler.object.clear();
			}
		}
		// Back Button for Help screen
		else if (game.gameState == STATE.Help) {
			if (mouseOver(mx, my, 566, 200, 133, 42)) {
				game.gameState = STATE.Menu;
				return;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * Helper method to detect is the mouse is over a "button" drawn via Graphics
	 * 
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
