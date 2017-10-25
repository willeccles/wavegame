package mainGame;

import java.awt.Color;
import java.util.Random;
import mainGame.net.ClientConnection;

/**
 * A type of game mode in the game
 * 
 * @author Kyle Gorman 10/16/17
 *
 */

public class SpawnMultiplayer {

	private Handler handler;
	private HUD hud;
	private Game game;
	private int trackerTimer;
	private Color trackerColor;
	private int count;
	private Player opponent;
	private Player player;
	private boolean playing = false;
	private ClientConnection client;

	public SpawnMultiplayer(Handler handler, HUD hud, Game game, Player p) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		this.player = p;
		handler.object.clear();
		hud.health = 100;
		hud.setScore(0);
		hud.setLevel(1);
		//different types of enemies added
		trackerTimer = 1000;
		trackerColor = Color.blue;
		count = 0;
	}

	public void tick() {
		// this means that the other player has not joined yet
		if (!playing) {
			// show a piece of text until the player joins
			while (!playing);
			// remove the piece of text added above
		}

		// updates the trackers color
		if(trackerTimer == 999) {
			trackerColor = Color.blue;
		} else if (trackerTimer == 500) {
			trackerColor = Color.black;
		} else if (trackerTimer == 0) {
			trackerTimer = 1000;
		}

		//prevents the trackers from spawning invisible 
		if(count == 1) {
			trackerTimer--;
		}
	}

	/**
	 * Used to start gameplay from the client.
	 * @param x1 The X coordinate of the player.
	 * @param y1 The Y coordinate of the player.
	 * @param x2 The X coordinate of the other player.
	 * @param y2 The Y coordinate of the other player.
	 */
	public void startPlaying(double x1, double y1, double x2, double y2) {
		player = new Player(x1, y1, ID.Player, this.handler, this.hud, this.game);
		opponent = new Player(x2, y2, ID.Player, this.handler, this.hud, this.game, new Color(255, 64, 64), true);
		handler.addObject(opponent);
		handler.addObject(player);
		playing = true;
	}

	public void setClient(ClientConnection cc) {
		this.client = cc;
	}

	public void spawnEntity(ID type, double x, double y, int option, String side) {
		switch (type) {
			case EnemyBasic:
				handler.addObject(new EnemyBasic(x, y, 9, 9, type, handler));
				break;
			case EnemySmart:
				handler.addObject(new EnemySmart(x, y, -5, type, handler));
				break;
			case EnemyBurst:
				handler.addObject(new EnemyBurst(x, y, 50, 50, 200, side, type, handler, true));
				break;
			case EnemySweep:
				switch (option) {
					case 0:
						handler.addObject(new EnemySweep(x, y, 15, 1, type, handler));
						break;
					case 1:
						handler.addObject(new EnemySweep(x, y, 15, -1, type, handler));
						break;
					case 2:
						handler.addObject(new EnemySweep(x, y, 15, 3, type, handler));
						break;
					case 3:
						handler.addObject(new EnemySweep(x, y, 15, -3, type, handler));
						break;
				}
				break;
			case EnemyShooter:
				handler.addObject(new EnemyShooter(x, y, 100, 100, -20, type, handler));
				break;
			case EnemyTracker:
				count = 1;
				handler.addObject(new EnemyTracker(x, y, -5, type, handler, trackerColor, trackerTimer));
				break;
			case EnemyExpand:
				handler.addObject(new EnemyExpand(Game.clampX(x, 100), Game.clampY(y, 100), 100, 100, type, handler));
				break;
			case EnemyMiniShooter:
				handler.addObject(new EnemyMiniShooter(Game.clampX(x, 75), Game.clampY(y, 75), 75, 75, -10, type, handler));
				break;
			case EnemyPorcupine:
				handler.addObject(new EnemyPorcupine(Game.clampX(x, 100), Game.clampY(y, 100), 100, 100, type, handler, -1, -2));
				break;
		}
	}
}
