package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import mainGame.Game.STATE;

/**
 * The main menu
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Menu {

	private Game game;
	private Handler handler;
	private HUD hud;
	private Image img;
	private int timer;
	private Random r;
	private ArrayList<Color> colorPick = new ArrayList<Color>();
	private int colorIndex;
	private Spawn1to5 spawner;

	public Menu(Game game, Handler handler, HUD hud, Spawn1to5 spawner) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.spawner = spawner;
		timer = 10;
		r = new Random();
		addColors();
		img = null;
		
		try {
			URL imageURL = Game.class.getResource("images/backgroundimage.jpg");
			img = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}

		handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 500, 50, 50, 0, -2,
				colorPick.get(r.nextInt(6)), ID.Firework, this.handler));
	}

	public void addColors() {
		colorPick.add(Color.white);
		colorPick.add(Color.white);
		colorPick.add(Color.white);
		colorPick.add(Color.white);
		colorPick.add(Color.white);
		colorPick.add(Color.white);
		colorPick.add(Color.white);
	}

	public void tick() {
		game.gameState = STATE.Menu;
		timer--;
		if (timer <= 0) {
			handler.object.clear();
			colorIndex = r.nextInt(6);
			handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 720, 100, 100, 0, -4, colorPick.get(colorIndex), ID.Firework, this.handler));
			timer = 300;
		}
		handler.tick();
	}

	public void render(Graphics g) {
		if (game.gameState == STATE.Menu) {
			g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
			handler.render(g);
			Font font = new Font("Amoebic", 1, 50);
			Font font2 = new Font("Amoebic", 1, 40);

			g.setFont(font);
			g.setColor(Color.orange);
			g.drawString("Game Modes", 792, 66);

			g.setFont(font);
			g.setColor(Color.orange);
			g.drawString("Player Known BattleLands", 20, 66);

			g.setColor(Color.orange);
			g.drawRect(660, 90, 266, 266);
			g.setFont(font2);
			g.setColor(Color.orange);
			g.drawString("Waves", 740, 143);

			g.setColor(Color.orange);
			g.drawRect(960, 90, 266, 266);
			g.setFont(font2);
			g.setColor(Color.orange);
			g.drawString("Bosses", 1033, 143);

			g.setColor(Color.orange);
			g.drawRect(660, 390, 266, 266);
			g.setFont(font2);
			g.setColor(Color.orange);
			g.drawString("Attack", 730, 443);

			g.setColor(Color.orange);
			g.drawRect(960, 390, 266, 266);
			g.setFont(font2);
			g.setColor(Color.orange);
			g.drawString("Survival", 1033, 443);

			g.setColor(Color.orange);
			g.drawRect(53, 90, 566, 166);
			g.setFont(font);
			g.setColor(Color.orange);
			g.drawString("Help", 266, 186);

			g.setColor(Color.orange);
			g.drawRect(53, 290, 566, 166);
			g.setFont(font);
			g.setColor(Color.orange);
			g.drawString("Credits", 226, 400);

			g.setColor(Color.orange);
			g.drawRect(53, 490, 566, 166);
			g.setFont(font);
			g.setColor(Color.orange);
			g.drawString("Quit", 266, 600);

		} else if (game.gameState == STATE.Help) {// if the user clicks on "help"
			Font font = new Font("impact", 1, 33);
			Font font2 = new Font("impact", 1, 20);

			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Help", 600, 46);

			g.setFont(font2);

			g.drawString("Waves: Use arrow keys to move player. Within Waves your goal is to, avoid enemies until the player is teleported to a new level." 
					, 26, 133);
			g.drawString("After beating 4 levels on level 5, there will be a boss fight that rewards you with a single use Power Up.", 53, 166);

			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect(566, 200, 133, 42);
			g.drawString("Back", 613, 226);
		}
	}
}
