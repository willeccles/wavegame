package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Leaderboard extends JPanel {
	private Game game;
	int x, y, charLoc;
	String user;
	
	public Leaderboard(Game game){
		this.game = game;
		x = 353;
		y = 490;
		user = "";
		charLoc = 0;
	}

	public void paint (Graphics g) {
		// TODO Auto-generated method stub
		//1280x720
		Font font = new Font("Comic Sans MS", 1 , 66);
		g.setColor(Color.white);
		g.drawRect(x, y, 566, 166);
		
		g.setFont(font);
		g.drawString("Done", x+213, y+96);

		g.setColor(Color.white);
		g.drawRect(x, y-200, 566, 166);
		
		g.setFont(font);
		g.drawString(user, x+213, y-100);
	}

	public void updateUser(String temp){
		if(temp.equals("back")){
			user = user.substring(0, charLoc);
		} else if (temp.equals("empty")) {
			user = "";
		} else {
			user = user + temp;
		}
		repaint();
		System.out.println(user);
	}
	public String getUser(){
		return user;
	}
	public void updateLoc(int temp){
		if(temp == 0){
			charLoc = 0;
		} else {
			charLoc = charLoc + temp;
		}
	}
	public int getLoc(){
		return charLoc;
	}

}
