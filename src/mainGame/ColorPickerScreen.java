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
	private Game game;
	private int x,y;
	
	public ColorPickerScreen(Player player, Game game) {
		this.player = player;
		this.game = game;
		x = 0;
		y = 0;
	}
	
	public void render(Graphics g) {
		if(game.gameState == STATE.Color) {
			g.setColor(Color.white);
			g.drawRect(x, y, 100, 100);
			g.fillRect(x, y, 100, 100);
			g.setColor(Color.blue);
			g.drawRect(x+100, y, 100, 100);
			g.fillRect(x+100, y, 100, 100);
			g.setColor(Color.yellow);
			g.drawRect(x+200, y, 100, 100);
			g.fillRect(x+200, y, 100, 100);
			g.setColor(Color.cyan);
			g.drawRect(x+300, y, 100, 100);
			g.fillRect(x+300, y, 100, 100);
			g.setColor(Color.gray);
			g.drawRect(x+400, y, 100, 100);
			g.fillRect(x+400, y, 100, 100);
			g.setColor(Color.green);
			g.drawRect(x, y+100, 100, 100);
			g.fillRect(x, y+100, 100, 100);
			g.setColor(Color.magenta);
			g.drawRect(x+100, y+100, 100, 100);
			g.fillRect(x+100, y+100, 100, 100);
			g.setColor(Color.orange);
			g.drawRect(x+200, y+100, 100, 100);
			g.fillRect(x+200, y+100, 100, 100);
			g.setColor(Color.pink);
			g.drawRect(x+300, y+100, 100, 100);
			g.fillRect(x+300, y+100, 100, 100);
			g.setColor(Color.red);
			g.drawRect(x+400, y+100, 100, 100);
			g.fillRect(x+400, y+100, 100, 100);
			
			
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
