package mainGame;

import java.awt.Color;
import java.awt.Font;
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
	int x, y;
	
	public Leaderboard(Game game){
		this.game = game;
		x = 353;
		y = 490;

//	private JButton submitButton;
//	private JTextField textBox;
//	private boolean isShown = false; // whether or not the stuff is visible on the screen
//	private JPanel panel;
//	private String namePattern = "^[a-zA-Z_0-9]{1,15}$"; // regex that matches a valid username
//	
//	public Leaderboard(Game game){
//		this.game = game;
//		this.panel = new JPanel();
//		panel.setLayout(null);
//		panel.setSize(new Dimension(Game.WIDTH, Game.HEIGHT));
//		panel.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
//		panel.setMinimumSize(new Dimension(Game.WIDTH, Game.HEIGHT));
//		panel.setBackground(Color.BLACK);
//		textBox = new JTextField(20);
//		textBox.setHorizontalAlignment(JTextField.CENTER);
//		textBox.setFont(new Font("Amoebic", 1, 40));
//		submitButton = new JButton("Submit Score");
//		submitButton.setFont(new Font("Amoebic", 0, 20));
//		submitButton.addActionListener((ae) -> {
//			// this is what happens when the button is clicked
//			if (textBox.getText().matches(namePattern)) {
//				// valid name, so we can do what we gotta do
//			} else {
//				// invalid name, so we should tell the user somehow
//			}
//		});
//		textBox.addActionListener((ae) -> {
//		});
//
//		panel.add(textBox);
//		panel.add(submitButton);
//		
//		// set the locations of the textbox and the button:
//		textBox.setLocation(1280/2 - (int)textBox.getPreferredSize().getWidth()/2, 720/2 - (int)textBox.getPreferredSize().getHeight() - 10);
//		submitButton.setLocation(1280/2 - (int)submitButton.getPreferredSize().getWidth()/2, 720/2 + 10);
//>>>>>>> 401b78232bfdc70839f308aba22d77739203a354
	}

	public void render(Graphics g) {
		// TODO Auto-generated method stub
		//1280x720
		Font font = new Font("Comic Sans MS", 1 , 66);
		g.setColor(Color.white);
		g.drawRect(x, y, 566, 166);
		g.setFont(font);
		g.drawString("Done", x+213, y+96);
		
		g.setColor(Color.white);
		g.drawRect(x, y-200, 566, 166);
	}
	
	public String updateUser(String temp){
		return temp;
	}
	public int updateLoc(int temp){
		return temp;
		this.render(g);
	}

}
