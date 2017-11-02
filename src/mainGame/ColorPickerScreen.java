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

public class ColorPickerScreen {

	private Player player;
	private Image img;
	private Game game;

	public ColorPickerScreen(Player player, Game game) {
		this.player = player;
		this.game = game;
		img = null;

		try {
			URL imageURL = Game.class.getResource("images/colorPicker.jpg");
			img = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g) {
		if(game.gameState == STATE.Color) {
			g.drawImage(img, 0, 0, 1000, 720, null);
		}
	}
	public void tick() {
		game.gameState = STATE.Color;
	}	
}
