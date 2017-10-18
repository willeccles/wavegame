package mainGame;

import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;

public class Leaderboard {
	private Game game;
	private JButton submitButton;
	private JTextField textBox;
	private boolean isShown = false; // whether or not the stuff is visible on the screen
	private JPanel panel;
	private String namePattern = "^[a-zA-Z_0-9]{1,15}$"; // regex that matches a valid username
	
	public Leaderboard(Game game){
		this.game = game;
		this.panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(new Dimension(Game.WIDTH, Game.HEIGHT));
		panel.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
		panel.setMinimumSize(new Dimension(Game.WIDTH, Game.HEIGHT));
		panel.setBackground(Color.BLACK);
		textBox = new JTextField(20);
		textBox.setHorizontalAlignment(JTextField.CENTER);
		textBox.setFont(new Font("Amoebic", 1, 40));
		submitButton = new JButton("Submit Score");
		submitButton.setFont(new Font("Amoebic", 0, 20));
		submitButton.addActionListener((ae) -> {
			// this is what happens when the button is clicked
			if (textBox.getText().matches(namePattern)) {
				// valid name, so we can do what we gotta do
			} else {
				// invalid name, so we should tell the user somehow
			}
		});
		textBox.addActionListener((ae) -> {
		});

		panel.add(textBox);
		panel.add(submitButton);
		
		// set the locations of the textbox and the button:
		textBox.setLocation(1280/2 - (int)textBox.getPreferredSize().getWidth()/2, 720/2 - (int)textBox.getPreferredSize().getHeight() - 10);
		submitButton.setLocation(1280/2 - (int)submitButton.getPreferredSize().getWidth()/2, 720/2 + 10);
	}

	public void render(Graphics g) {
		// we don't care about the Graphics, we aren't drawing
		// we just need to make sure that the button and textbox are visible
		if (!isShown) {
			game.getFrame().add(panel);
			game.getFrame().pack();
			panel.setVisible(true);
			textBox.requestFocus();
			isShown = true;
		}
		panel.paint(g);
	}

}
