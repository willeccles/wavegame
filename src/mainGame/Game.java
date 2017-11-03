package mainGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Dimension;
import javafx.embed.swing.JFXPanel;
import javax.swing.JFrame;
import mainGame.audio.SoundPlayer;
import mainGame.net.ClientConnection;

/**
 * Main game class. This class is the driver class and it follows the Holder
 * pattern. It houses references to ALL of the components of the game
 * 
 * @author Brandon Loehle 5/30/16
 */

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 1280, HEIGHT = 720;
	private Thread thread;
	private boolean running = false;

	private Handler handler;
	private HUD hud;
	private Spawn1to5 spawner;
	private Spawn5to10 spawner2;
	private Spawn10to15 spawner3;
	private Spawn15to20 spawner4;
	private SpawnSurvival spawnSurvival;
	private Menu menu;
	private GameOver gameOver;
	private UpgradeScreen upgradeScreen;
	private MouseListener mouseListener;
	private Upgrades upgrades;
	private Player player;
	public STATE gameState = STATE.Menu;
	private PauseMenu pauseMenu;
	public static int TEMP_COUNTER;
	private SoundPlayer soundplayer;
	private Leaderboard leaderboard;

	private SpawnBosses spawnBosses;
	private SpawnMultiplayer spawnMultiplayer;
	private JFrame frame;
	private boolean isPaused = false;
	private SpawnTest spawnTest;
	private boolean isMusicPlaying = true;

	/* NOBODY TOUCH THESE VARS, THEY ARE FOR TESTING NETWORKING */
	private String op;
	private String addr;
	private int port;
	private String room;
	private String pass;
	private ColorPickerScreen colorScreen;
	
	/**
	 * Used to switch between each of the screens shown to the user
	 */
	public enum STATE {
		Menu, Help, Connect, Wave, GameOver, Upgrade, Bosses, Survival, Multiplayer, 
		Leaderboard, Test, Color
	};

	/**
	 * Initialize the core mechanics of the game
	 * @param op The operation (join/host/none) to use
	 * @param addr The address to use
	 * @param port The port
	 * @param room The roomname
	 * @param pass The password
	 */
	public Game(String op, String addr, int port, String room, String pass) {
		handler = new Handler();
		hud = new HUD(this);
		player = new Player(WIDTH / 2 - 21, HEIGHT / 2 - 21, ID.Player, handler, this.hud, this);
		spawner = new Spawn1to5(this.handler, this.hud, this);
		spawner2 = new Spawn5to10(this.handler, this.hud, this.spawner, this);
		spawner3 = new Spawn10to15(this.handler, this.hud, this);
		spawner4 = new Spawn15to20(this.handler, this.hud, this);
		spawnSurvival = new SpawnSurvival(this.handler, this.hud, this);
		spawnBosses = new SpawnBosses(this.handler, this.hud, this);
		spawnMultiplayer = new SpawnMultiplayer(this.handler, this.hud, this, this.player);
		spawnTest = new SpawnTest(this.handler, this.hud, this);
		menu = new Menu(this, this.handler, this.hud, this.spawner);
		upgradeScreen = new UpgradeScreen(this, this.handler, this.hud);
		upgrades = new Upgrades(this, this.handler, this.hud, this.upgradeScreen, this.player, this.spawner, this.spawner2, this.spawnTest);
		gameOver = new GameOver(this, this.handler, this.hud, player);
		pauseMenu = new PauseMenu();
		leaderboard = new Leaderboard(this, hud);
		mouseListener = new MouseListener(this, this.handler, this.hud, this.spawner, this.spawner2, this.spawnSurvival, this.upgradeScreen, this.player, this.upgrades, leaderboard, this.spawnBosses, this.spawnTest);
		this.addKeyListener(new KeyInput(this.handler, this, this.hud, this.player, this.spawner, this.upgrades, this.leaderboard));
		this.addMouseListener(mouseListener);
		// technically, this is bad practice but I don't care right now
		this.setSize(new Dimension(WIDTH, HEIGHT));
		JFXPanel jfxp = new JFXPanel(); // trust
		soundplayer = new SoundPlayer("sounds/main.mp3", true);
		soundplayer.start();
		new Window((int) WIDTH, (int) HEIGHT, "PlayerKnown's BattleLands", this);
		colorScreen = new ColorPickerScreen(player, this);

		this.op = op;
		this.addr = addr;
		this.port = port;
		this.room = room;
		this.pass = pass;
	}

	/**
	 * The thread is simply a programs path of execution. This method ensures
	 * that this thread starts properly.
	 */
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Best Java game loop out there (used by Notch!)
	 */
	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();// 60 times a second, objects are being updated
				delta--;
			}
			if (running)
				render();// 60 times a second, objects are being drawn
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// disabled because i like my battery life
				// System.out.println("FPS: " + frames);
				// System.out.println(gameState);
				// System.out.println(Spawn1to5.LEVEL_SET);
				frames = 0;
			}
		}
		stop();

	}

	/**
	 * Constantly ticking (60 times per second, used for updating smoothly).
	 * Used for updating the instance variables (DATA) of each entity (location,
	 * health, appearance, etc).
	 */
	private void tick() {
		// if the arguments are given, go straight for multiplayer
		if (!op.equals("none")) {
			try {
				spawnMultiplayer.createClient(addr, port);
				if (op.equals("host")) {
					spawnMultiplayer.getClient().host_game(room, pass);
				} else if (op.equals("join")) {
					spawnMultiplayer.getClient().join_game(room, pass);
				}
				gameState = STATE.Multiplayer;
				op = "none";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!isPaused()) { // only tick the game modes and stuff if the game is not paused
			handler.tick();// ALWAYS TICK HANDLER, NO MATTER IF MENU OR GAME
			// SCREEN
			if (gameState == STATE.Wave) {// game is running
				if (!handler.isPaused()) {
					hud.tick();
					if (Spawn1to5.LEVEL_SET == 1) {// user is on levels 1 thru 5, update them
						spawner.tick();
					} else if (Spawn1to5.LEVEL_SET == 2) {// user is on levels 5 thru 10, update them
						spawner2.tick();
					} else if (Spawn1to5.LEVEL_SET == 3) {
						spawner3.tick();
					} else if (Spawn1to5.LEVEL_SET == 4) {
						spawner4.tick();
					}
				}
				// switch the sound that's playing if the mode is waves
				if (!soundplayer.getSong().equals("sounds/memories.mp3")) {
					soundplayer.stop_playing();
					soundplayer = new SoundPlayer("sounds/memories.mp3", true);
					soundplayer.start();
				}
			} else if (gameState == STATE.Menu || gameState == STATE.Help) {// user is on menu, update the menu items
				menu.tick();
				// make sure the menu is playing the right song
				if (!soundplayer.getSong().equals("sounds/main.mp3")) {
					soundplayer.stop_playing();
					soundplayer = new SoundPlayer("sounds/main.mp3", true);
					soundplayer.start();
				}
			} else if (gameState == STATE.Upgrade) {// user is on upgrade
				// screen, update the
				// upgrade screen
				upgradeScreen.tick();
			} else if (gameState == STATE.GameOver) {// game is over, update the
				// game over screen
				gameOver.tick();
			} else if (gameState == STATE.Connect) { // entering connection info for MP
				// TODO: add a connect screen @chieco
			} else if (gameState == STATE.Multiplayer) {
				// do not use HUD::tick() here, it's used inside the spawner
				spawnMultiplayer.tick();
			} else if (gameState == STATE.Bosses) {
				hud.tick();
				spawnBosses.tick();
				if (!soundplayer.getSong().equals("sounds/bosses.mp3")) {
					soundplayer.stop_playing();
					soundplayer = new SoundPlayer("sounds/bosses.mp3", true);
					soundplayer.start();
				}
			} else if (gameState == STATE.Survival) {
				hud.tick();
				spawnSurvival.tick();
				if (!soundplayer.getSong().equals("sounds/135.mp3")) {
					soundplayer.stop_playing();
					soundplayer = new SoundPlayer("sounds/135.mp3", true);
					soundplayer.start();
				}
			} else if (gameState == STATE.Test) {// game is running
				hud.tick();
				spawnTest.tick();
			}
		}

		if(isMusicPlaying) {
			if (soundplayer.isPaused())
				soundplayer.play();
		} else {
			if (!soundplayer.isPaused())
				soundplayer.pause();
		}
	}

	/**
	 * Constantly drawing to the many buffer screens of each entity requiring
	 * the Graphics objects (entities, screens, HUD's, etc).
	 */
	private void render() {

		/*
		 * BufferStrategies are used to prevent screen tearing. In other words,
		 * this allows for all objects to be redrawn at the same time, and not
		 * individually
		 */
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		///////// Draw things below this/////////////

		g.setColor(Color.black);
		g.fillRect(0, 0, (int) WIDTH, (int) HEIGHT);

		handler.render(g); // ALWAYS RENDER HANDLER, NO MATTER IF MENU OR GAME
		// SCREEN
		if (!isPaused()) {
			if (gameState == STATE.Wave || gameState == STATE.Multiplayer 
					|| gameState == STATE.Bosses || gameState == STATE.Survival
					|| gameState == STATE.Test) {// user is playing game, draw game objects
				hud.render(g);
			} else if (gameState == STATE.Menu || gameState == STATE.Help) {// user
				// is in
				// help
				// or
				// the
				// menu,
				// draw
				// the
				// menu
				// and help objects
				menu.render(g);
			} else if (gameState == STATE.Upgrade) {// user is on the upgrade
				// screen, draw the upgrade
				// screen
				upgradeScreen.render(g);
			} else if (gameState == STATE.GameOver) {// game is over, draw the game
				// over screen
				gameOver.render(g);
			} else if (gameState == STATE.Leaderboard) {
				leaderboard.paint(g);
			} else if (gameState == STATE.Color) {
				colorScreen.render(g);
			}
		} else {
			pauseMenu.render(g);
		}
		///////// Draw things above this//////////////
		g.dispose();
		bs.show();
	}

	/**
	 * 
	 * Constantly checks bounds, makes sure players, enemies, and info doesn't
	 * leave screen
	 * 
	 * @param var
	 *            x or y location of entity
	 * @param min
	 *            minimum value still on the screen
	 * @param max
	 *            maximum value still on the screen
	 * @return value of the new position (x or y)
	 */
	public static double clamp(double var, double min, double max) {
		if (var >= max)
			return max;
		else if (var <= min)
			return min;
		else
			return var;
	}

	public static double clampX(double x, double width) {
		return clamp(x, 0, Game.WIDTH - width);
	}

	public static double clampY(double y, double height) {
		return clamp(y, 0, Game.HEIGHT - height);
	}

	public static void main(String[] args) {
		String op = "none";
		String address = "none";
		int port = 0;
		String room = "";
		String pass = "";
		if (args.length == 5) {
			op = args[0];
			address = args[1];
			port = Integer.parseInt(args[2]);
			room = args[3];
			pass = args[4];
		}
		new Game(op, address, port, room, pass);
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public void pause() {
		isPaused = true;
	}

	public void unPause() {
		isPaused = false;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public STATE getGameState() {
		return gameState;
	}


	public void musicKeyPressed() {
		isMusicPlaying = !isMusicPlaying;
	}

	/**
	 * Updates the server with the player's position (only in multiplayer).
	 */
	public void updatePlayerPosition() {
		if (gameState == STATE.Multiplayer) {
			spawnMultiplayer.sendPos();
		}
	}
}
