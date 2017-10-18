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
	}

}
