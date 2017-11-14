package mainGame;

import mainGame.Game.STATE;
import mainGame.spawn.*;
import mainGame.gui.*;

/**
 * The upgrades that a user can have (they modify the game for the user)
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Upgrades {

	private Game game;
	private Handler handler;
	private HUD hud;
	private Player player;
	private Spawn1to5 spawner;
	private Spawn5to10 spawner2;
	private UpgradeScreen upgradeScreen;
	private String ability;
	private SpawnTest spawnTest;

	public Upgrades(Game game, Handler handler, HUD hud, UpgradeScreen upgradeScreen, 
			Player player, Spawn1to5 spawner, Spawn5to10 spawner2, SpawnTest spawnTest) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.upgradeScreen = upgradeScreen;
		this.player = player;
		this.spawner = spawner;
		this.spawner2 = spawner2;
		this.ability = hud.getAbility();
		this.spawnTest = spawnTest;
	}

	public void clearScreenAbility() {
		handler.clearEnemies();
		hud.setAbilityUses(hud.getAbilityUses() - 1);
		if (hud.getAbilityUses() == 0) {
			ability = "";
			hud.setAbility(ability);
		}
	}

	public void decreasePlayerSize() {
		player.setPlayerSize(17);
	}

	public void extraLife() {
		hud.setExtraLives(hud.getExtraLives() + 1);
	}

	public void healthIncrease() {
		hud.healthIncrease();
	}

	public void healthRegeneration() {
		hud.setRegen();
	}

	public void improvedDamageResistance() {
		player.setDamage(1);
	}

	public void levelSkipAbility() {
		if(game.getGameState() == STATE.Wave) {
			handler.clearEnemies();
			hud.setLevel(hud.getLevel() + 1);
			if (Spawn1to5.LEVEL_SET == 1) {
				spawner.skipLevel();
			} else if (Spawn1to5.LEVEL_SET == 2) {
				spawner2.skipLevel();
			}
			hud.setAbilityUses(hud.getAbilityUses() - 1);
			if (hud.getAbilityUses() == 0) {
				ability = "";
				hud.setAbility(ability);
			}
		} else if (game.getGameState() == STATE.Survival) {
			handler.clearEnemies();
		} else if (game.getGameState() == STATE.Test) {
			handler.clearEnemies();
			spawnTest.skipLevel();
		}

	}

	public void freezeTimeAbility() {
		handler.pause();
		hud.setAbilityUses(hud.getAbilityUses() - 1);
		if (hud.getAbilityUses() == 0) {
			ability = "";
			hud.setAbility(ability);
		}
	}

	public void speedBoost() {
		Player.playerSpeed *= 2;
	}

	public String getAbility() {
		return ability;
	}

	/**
	 * Activate the correct upgrade
	 * 
	 * @param path
	 *            is to the image of the upgrade that was pressed by the user
	 */
	public void activateUpgrade(String path) {
		if (path.equals("images/clearscreenability.png")) {
			ability = "clearScreen";
			hud.setAbility(ability);
			hud.setAbilityUses(3);
		} else if (path.equals("images/decreaseplayersize.png")) {
			decreasePlayerSize();
		} else if (path.equals("images/extralife.png")) {
			extraLife();
		} else if (path.equals("images/healthincrease.png")) {
			healthIncrease();
		} else if (path.equals("images/healthregeneration.png")) {
			healthRegeneration();
		} else if (path.equals("images/improveddamageresistance.png")) {
			improvedDamageResistance();
		} else if (path.equals("images/levelskipability.png")) {
			ability = "levelSkip";
			hud.setAbility(ability);
			hud.setAbilityUses(1);
		} else if (path.equals("images/freezetimeability.png")) {
			ability = "freezeTime";
			hud.setAbility(ability);
			hud.setAbilityUses(5);
		} else if (path.equals("images/speedboost.png")) {
			speedBoost();
		}

	}

	public void resetUpgrades() {
		Player.playerSpeed = 10;
		hud.resetHealth();
		hud.resetRegen();
		hud.setExtraLives(0);
		player.setPlayerSize(21);
		upgradeScreen.resetPaths();
		hud.setAbilityUses(0);
		hud.clearUpgrades();
	}

}
