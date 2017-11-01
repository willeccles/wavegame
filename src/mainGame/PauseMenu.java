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

public class PauseMenu {

	private Game game;
	private Handler handler;
	private HUD hud;
	private Image img;
	private int timer;
	private ArrayList<Color> colorPick = new ArrayList<Color>();

	public PauseMenu(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
	
		img = null;
		try {
			URL imageURL = Game.class.getResource("images/PauseMenu.png");
			img = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		timer--;
		if (timer <= 0) {
			timer = 300;
		}
		handler.tick();
	}

	public void render(Graphics g) {
		if (game.gameState == STATE.PauseMenu) {
			g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		//	handler.render(g);
		}
	}
}
