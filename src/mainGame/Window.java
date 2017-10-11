package mainGame;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Loads the window of the game, and sets the proper dimensions
 * @author Brandon Loehle
 * 5/30/16
 */

public class Window {

	private static final long serialVersionUID = 1L;

	public Window(int width, int height, String title, Game game) {
		JFrame frame = new JFrame(title);
		/*
		   int _width = width + frame.getInsets().left + frame.getInsets().right;
		   int _height = height + frame.getInsets().top + frame.getInsets().bottom;
		   frame.setPreferredSize(new Dimension(_width, _height));
		   frame.setMaximumSize(new Dimension(_width, _height));
		   frame.setMinimumSize(new Dimension(_width, _height));
		   */
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.pack();
		frame.setVisible(true);
		game.start();

	}


}
