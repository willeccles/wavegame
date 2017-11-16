package mainGame.gui;

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
import mainGame.spawn.*;
import mainGame.*;
import mainGame.gfx.*;

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
	private Image img2;
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
		img2 = null;

		try {
			URL imageURL = Game.class.getResource("images/backgroundgif.gif");
			img = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			URL imageURL = Game.class.getResource("images/paintbucket.png");
			img2 = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}

		handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 500, 50, 50, 0, -2,
				colorPick.get(r.nextInt(6)), ID.Firework, this.handler));
	}

	public void addColors() {
		colorPick.add(Color.white);
		colorPick.add(Color.white);
		colorPick.add(Color.yellow);
		colorPick.add(Color.yellow);
		colorPick.add(Color.orange);
		colorPick.add(Color.orange);
	}

	public void tick() {
		timer--;
		if (timer <= 0) {
			handler.object.clear();
			colorIndex = r.nextInt(6);
			handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 720, 100, 100, 0, -4,
					colorPick.get(colorIndex), ID.Firework, this.handler));
			timer = 300;
		}
		handler.tick();
	}

	public void render(Graphics g) {
		if (game.gameState == STATE.Menu) {
			g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
			g.drawImage(img2, 555, 395, 175, 175, null);
			handler.render(g);

			Font font = new Font("Amoebic", 1, 100);
			Font font2 = new Font("Amoebic", 1, 70);
			Font font3 = new Font("Amoebic", 1, 50);
			Font font4 = new Font("Amoebic", 1, 80);
			Font font5 = new Font("Amoebic", 1, 60);

			g.setFont(font4);
			g.setColor(Color.orange);
			g.drawString("Player Known BattleLands", 140, 70);

			g.setColor(Color.orange);
			g.drawRect(30, 90, 1220, 120);
			g.setFont(font);
			g.setColor(Color.orange);
			g.drawString("Waves", 475, 185);

			g.setColor(Color.orange);
			g.drawRect(30, 240, 600, 120);
			g.setFont(font3);
			g.setColor(Color.orange);
			g.drawString("Join", 275, 350);
			g.setFont(font5);
			g.drawString("Multiplayer:", 170, 290);
			g.drawString("Multiplayer:", 790, 290);
			
			
			g.setColor(Color.orange);
			g.drawRect(640, 240, 610, 120);
			g.setFont(font3);
			g.setColor(Color.orange);
			g.drawString("Host", 900, 350);

			g.setColor(Color.orange);
			g.drawRect(70, 390, 450, 180);
			g.setFont(font2);
			g.setColor(Color.orange);
			g.drawString("Bosses", 180, 500);

			g.setColor(Color.orange);
			g.drawRect(760, 390, 450, 180);
			g.setFont(font2);
			g.setColor(Color.orange);
			g.drawString("Survival", 860, 500);

			g.setColor(Color.orange);
			g.drawRect(40, 600, 380, 90);
			g.setFont(font3);
			g.setColor(Color.orange);
			g.drawString("LeaderBoards", 65, 660);

			g.setColor(Color.orange);
			g.drawRect(440, 600, 380, 90);
			g.setFont(font3);
			g.setColor(Color.orange);
			g.drawString("Help", 580, 660);

			g.setColor(Color.orange);
			g.drawRect(840, 600, 390, 90);
			g.setFont(font3);
			g.setColor(Color.orange);
			g.drawString("Quit", 980, 660);

		} else if (game.gameState == STATE.Help) {// if the user clicks on
			// "help"

			g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
			handler.render(g);

			img = null;

			try {
				URL imageURL = Game.class.getResource("images/backgroundgif.gif");
				img = Toolkit.getDefaultToolkit().getImage(imageURL);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Font font = new Font("impact", 1, 33);
			Font font2 = new Font("impact", 1, 20);

			g.setFont(font);
			g.setColor(Color.orange);
			g.drawString("Help", 600, 46);

			g.drawString("Use arrow keys to move the player.", 400, 100);
			g.setFont(font);
			g.drawString("Waves:", 100, 160);
			g.setFont(font2);
			g.drawString(
					" Within Waves, your goal is to avoid enemies until the player is transported to the new level. After beating 5 levels,",
					100, 190);
			g.drawString(" the next level will be a boss fight that rewards you with a single use Upgrade if you manage to defete one.",
					100, 220);
			 g.drawString("You can trigger the your upgrades by pressing shift", 100, 250);
			g.setFont(font);
			g.drawString("Bosses:", 100, 320);
			g.setFont(font2);
			g.drawString(
					"Within Bosses, your goal is to take on endless amount of bosses from the Wavese game mode and survive. As long",
					100, 350);
			g.drawString(" as possible to get first place on the leaderbaord!", 100, 380);
			g.setFont(font);
			g.drawString("Survival:", 100, 450);
			g.setFont(font2);
			g.drawString(
					" Within Survial, your goal is to survive as long as possible while enemies spawn and try to kill you. Pick ups are availble",
					100, 480);
			g.drawString(
					" to help you stay alive. If you pick up ham, you regain health, if you pick up shoes, you earn a light speed boost and if you ",
					100, 510);
			g.drawString("pick up a coin, you earn add 1000 points to your score. If you pick up a coin, you earn add 1000 points to your score.", 100, 540);
			g.drawString("Press the m key to mute the music." , 100, 590);
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect(566, 590, 133, 42);
			g.drawString("Back", 613, 620);
		}
	}
}
