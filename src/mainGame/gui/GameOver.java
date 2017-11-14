package mainGame.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import com.sun.glass.ui.EventLoop.State;

import mainGame.Game.STATE;
import mainGame.*;

/**
 * The game over screen
 * 
 * @author Brandon Loehle 5/30/16
 *
 */
public class GameOver {

	private Game game;
	private Handler handler;
	private HUD hud;
	private Color retryColor;
	private String text;
	private Player player;
	private String backText;
	private String message;

	public GameOver(Game game, Handler handler, HUD hud, Player player) {
		this.game = game;
		this.handler = handler;
		this.player = player;
		this.hud = hud;
		this.retryColor = Color.white;
		text = "Game Over";
		backText = "Click anywhere to return to the menu";
	}

	public void tick() {
		handler.clearPlayer();
	}

	public void render(Graphics g) {
		Font font = new Font("Amoebic", 1, 100);
		Font font2 = new Font("Amoebic", 1, 60);

		g.setFont(font);
		g.setColor(this.retryColor);

		text = "Game Over";
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font, text) / 2, Game.HEIGHT / 2 - 150);
		g.setFont(font2);
		if(!(player.checkGame().equals("survival")) && !player.checkGame().equals("multiplayer")) {
			text = "Level: " + hud.getLevel();
			g.drawString(text, Game.WIDTH / 2 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 - 50);
			text = "Score: " + hud.getScore();
			g.drawString(text, Game.WIDTH / 2 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 + 50);
		} else {
			text = "Score: " + hud.getScore();
			g.drawString(text, Game.WIDTH / 2 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 - 50);
		}
		if (handler.isMulti()) {
			g.drawString(message, Game.WIDTH / 2 - getTextWidth(font2, message) / 2, Game.HEIGHT / 2 + 50);
		}
		g.setFont(font2);
		g.drawString(backText, Game.WIDTH / 2 - getTextWidth(font2, backText) / 2, Game.HEIGHT / 2 + 150);

	}

	public void setMessage(String msg) {
		message = msg;
	}

	public void setBackText(String msg) {
		backText = msg;
	}

	/**
	 * Function for getting the pixel width of text
	 * 
	 * @param font
	 *            the Font of the test
	 * @param text
	 *            the String of text
	 * @return width in pixels of text
	 */
	public static int getTextWidth(Font font, String text) {
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at, true, true);
		int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
		return textWidth;
	}

}
