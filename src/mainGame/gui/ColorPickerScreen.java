package mainGame.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
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
			
			Font font = new Font("Amoebic", 1, 80);
			g.setFont(font);
			g.setColor(Color.gray);
			g.drawString("Head Color", 425, 65);
			g.drawString("Tail Color", 450, 425);
			
			g.setColor(Color.white);
			g.drawOval(x+70, y+75, 125, 125);
			g.fillOval(x+70, y+75, 125, 125);
			g.setColor(Color.blue);
			g.drawOval(x+320, y+75, 125, 125);
			g.fillOval(x+320, y+75, 125, 125);
			g.setColor(Color.yellow);
			g.drawOval(x+570, y+75, 125, 125);
			g.fillOval(x+570, y+75, 125, 125);
			g.setColor(Color.cyan);
			g.drawOval(x+820, y+75, 125, 125);
			g.fillOval(x+820, y+75, 125, 125);
			g.setColor(Color.gray);
			g.drawOval(x+1070, y+75, 125, 125);
			g.fillOval(x+1070, y+75, 125, 125);
			g.setColor(Color.green);
			g.drawOval(x+70, y+215, 125, 125);
			g.fillOval(x+70, y+215, 125, 125);
			g.setColor(Color.magenta);
			g.drawOval(x+320, y+215, 125, 125);
			g.fillOval(x+320, y+215, 125, 125);
			g.setColor(Color.orange);
			g.drawOval(x+570, y+215, 125, 125);
			g.fillOval(x+570, y+215, 125, 125);
			g.setColor(Color.pink);
			g.drawOval(x+820, y+215, 125, 125);
			g.fillOval(x+820, y+215, 125, 125);
			g.setColor(Color.red);
			g.drawOval(x+1070, y+215, 125, 125);
			g.fillOval(x+1070, y+215, 125, 125);
			
			g.setColor(Color.white);
			g.drawOval(x+70, y+440, 125, 125);
			g.fillOval(x+70, y+440, 125, 125);
			g.setColor(Color.blue);
			g.drawOval(x+320, y+440, 125, 125);
			g.fillOval(x+320, y+440, 125, 125);
			g.setColor(Color.yellow);
			g.drawOval(x+570, y+440, 125, 125);
			g.fillOval(x+570, y+440, 125, 125);
			g.setColor(Color.cyan);
			g.drawOval(x+820, y+440, 125, 125);
			g.fillOval(x+820, y+440, 125, 125);
			g.setColor(Color.gray);
			g.drawOval(x+1070, y+440, 125, 125);
			g.fillOval(x+1070, y+440, 125, 125);
			g.setColor(Color.green);
			g.drawOval(x+70, y+585, 125, 125);
			g.fillOval(x+70, y+585, 125, 125);
			g.setColor(Color.magenta);
			g.drawOval(x+320, y+585, 125, 125);
			g.fillOval(x+320, y+585, 125, 125);
			g.setColor(Color.orange);
			g.drawOval(x+570, y+585, 125, 125);
			g.fillOval(x+570, y+585, 125, 125);
			g.setColor(Color.pink);
			g.drawOval(x+820, y+585, 125, 125);
			g.fillOval(x+820, y+585, 125, 125);
			g.setColor(Color.red);
			g.drawOval(x+1070, y+585, 125, 125);
			g.fillOval(x+1070, y+585, 125, 125);
			
			
			Font font2 = new Font("Amoebic", 1, 50);
			g.setColor(Color.gray);
			g.drawRect(1000, 10, 250, 60);
			g.setFont(font2);
			g.setColor(Color.gray);
			g.drawString("Menu", 1065, 55);
		}
	}	
}
