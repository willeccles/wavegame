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
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import mainGame.Game.STATE;

/**
 * After completing a boss, this screen appears. The upgrade stays effective the
 * rest of the game. A user cannot choose the same upgrade twice
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class UpgradeScreen {

	private Game game;
	private Handler handler;
	private HUD hud;
	private String text;
	private String[] paths = { "images/clearscreenability.png", "images/decreaseplayersize.png", "images/extralife.png",
			"images/healthincrease.png", "images/healthregeneration.png", "images/improveddamageresistance.png",
			"images/levelskipability.png", "images/freezetimeability.png", "images/speedboost.png" };
	private ArrayList<String> imagePaths = new ArrayList<String>();
	private Random r = new Random();
	private int index1, index2, index3;
	private int differentUpgrades;
	
	public UpgradeScreen(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		differentUpgrades = 9;
		setIndex();
		addPaths();
		text = "";
	}

	public void tick() {

	}

	public void render(Graphics g) {
		Font font = new Font("Amoebic", 1, 116);
		text = "Select an Upgrade!";
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(text, Game.WIDTH / 2 - getTextWidth(font, text) / 2, 133);

		g.drawImage(getImage(imagePaths.get(index1)), 210, 200, 860, 150, null);
		g.drawImage(getImage(imagePaths.get(index2)), 210, 200 + 150, 860, 150, null);
		g.drawImage(getImage(imagePaths.get(index3)), 210, 200 + 2 * 150, 860, 150, null);

	}

	/**
	 * Reset the paths to each picture
	 */
	public void resetPaths() {
		paths[0] = "images/clearscreenability.png";
		paths[1] = "images/decreaseplayersize.png";
		paths[2] = "images/extralife.png";
		paths[3] = "images/healthincrease.png";
		paths[4] = "images/healthregeneration.png";
		paths[5] = "images/improveddamageresistance.png";
		paths[6] = "images/levelskipability.png";
		paths[7] = "images/freezetimeability.png";
		paths[8] = "images/speedboost.png";

	}

	public void addPaths() {
		for (int i = 0; i < 9; i++) {
			imagePaths.add(paths[i]);
		}
	}

	public int getIndex(int maxIndex) {
		int index = r.nextInt(maxIndex);
		return index;
	}

	/**
	 * Gets 3 indices of pictures, and ensures that they are all different. These 3
	 * indices will load 3 different upgrade options for the user
	 */

	public Image getImage(String path) {
		Image image = null;
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return image;
	}

	public int getTextWidth(Font font, String text) {
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at, true, true);
		int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
		return textWidth;
	}

	/**
	 * Get the path of the image.
	 * 
	 * @param x
	 *            can be either a 1, 2, or 3 (as there are only three upgrade
	 *            options shown at one time)
	 * @return String path of image
	 */
	public String getPath(int x) {
		if (x == 1) {
			return paths[index1];
		} else if (x == 2) {
			return paths[index2];
		} else {
			return paths[index3];
		}
	}

	/**
	 * Removes the path of the image that is chosen by the user, so that it is never
	 * offered again
	 * 
	 * @param x
	 *            can be either a 1, 2, or 3 (as there are only three upgrade
	 *            options shown at one time)
	 */
	public void removeUpgradeOption(int x) {
		if (x == 1) {
			paths[index1] = null;
		} else if (x == 2) {
			paths[index2] = null;
		} else {
			paths[index3] = null;
		}
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}
	
	
	public void setIndex(){
		index1 = (int) (Math.random()*(differentUpgrades));
		index2 = (int) (Math.random()*(differentUpgrades));
		while(index1 == index2){
			index2 = (int) (Math.random()*(differentUpgrades));
		}
		index3 = (int) (Math.random()*(differentUpgrades));
		while(index1 == index3 || index2 == index3){
			index3 = (int) (Math.random()*(differentUpgrades));
		}
	}

	public void resetUpgradeScreen(){
		this.setIndex();
		this.resetPaths();
		this.addPaths();
	}
}
