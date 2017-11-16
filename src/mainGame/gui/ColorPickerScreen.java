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
import mainGame.*;

/**
 * The main menu
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class ColorPickerScreen {

	private Player player;
	private Game game;
	private int x,y;
	private Image img;
	
	public ColorPickerScreen(Player player, Game game) {
		this.player = player;
		this.game = game;
		x = 0;
		y = 0;
		img = null;
		try {
			URL imageURL = Game.class.getResource("images/colorpickerbackground.jpg");
			img = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g) {
		if(game.gameState == STATE.Color) {
			g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
			g.setColor(Color.white);
			g.drawOval(x+70, y+75, 150, 150);
			g.fillOval(x+70, y+75, 150, 150);
			g.setColor(Color.blue);
			g.drawOval(x+320, y+75, 150, 150);
			g.fillOval(x+320, y+75, 150, 150);
			g.setColor(Color.yellow);
			g.drawOval(x+570, y+75, 150, 150);
			g.fillOval(x+570, y+75, 150, 150);
			g.setColor(Color.cyan);
			g.drawOval(x+820, y+75, 150, 150);
			g.fillOval(x+820, y+75, 150, 150);
			g.setColor(Color.gray);
			g.drawOval(x+1070, y+75, 150, 150);
			g.fillOval(x+1070, y+75, 150, 150);
			g.setColor(Color.green);
			g.drawOval(x+70, y+250, 150, 150);
			g.fillOval(x+70, y+250, 150, 150);
			g.setColor(Color.magenta);
			g.drawOval(x+320, y+250, 150, 150);
			g.fillOval(x+320, y+250, 150, 150);
			g.setColor(Color.orange);
			g.drawOval(x+570, y+250, 150, 150);
			g.fillOval(x+570, y+250, 150, 150);
			g.setColor(Color.pink);
			g.drawOval(x+820, y+250, 150, 150);
			g.fillOval(x+820, y+250, 150, 150);
			g.setColor(Color.red);
			g.drawOval(x+1070, y+250, 150, 150);
			g.fillOval(x+1070, y+250, 150, 150);
			
			
			Font font = new Font("Amoebic", 1, 50);
			g.setColor(Color.white);
			g.drawRect(53, 490, 566, 166);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Menu", 266, 600);
		}
	}
	public void tick() {
		game.gameState = STATE.Color;
	}	
}
