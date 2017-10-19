package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Leaderboard extends JPanel {
	private Game game;
	private int x, y, charLoc, strX;
	private String user;
	private Boolean full;

	public Leaderboard(Game game){
		this.game = game;
		x = 353;
		y = 490;
		strX = x + 213;
		user = "";
		charLoc = 0;
		full = false;
	}

	public void paint (Graphics g) {
		// TODO Auto-generated method stub
		//1280x720
		Font font = new Font("Comic Sans MS", 1 , 50);
		g.setColor(Color.white);
		g.drawRect(x, y, 566, 166);

		g.setFont(font);
		g.drawString("Done", x+213, y+96);

		g.setColor(Color.white);
		g.drawRect(x, y-200, 566, 166);

		g.setFont(font);
		g.drawString(user, strX, y-100);

		if(full){
			g.setFont(font);
			g.drawString("15 character max", x+213, y-200);
		}
	}

	public void updateUser(String temp){
		if(charLoc == 15){
			full = true;
		} else {
			full = false;
			if(temp.equals("back")){
				user = user.substring(0, charLoc);
				if(charLoc%2 == 0)
					strX = strX + 20;
			} else if (temp.equals("empty")) {
				user = "";
				strX = x + 213;
			} else {
				user = user + temp;
				if(charLoc%2 == 0)
					strX = strX - 20;
			}
		}
		repaint();
		System.out.println(user);
	}
	public String getUser(){
		return user;
	}
	public void updateLoc(int temp){
		if(!full || temp <= 0){
			if(temp == 0){
				charLoc = 0;
			} else {
				charLoc = charLoc + temp;
			}
		}
	}
	public int getLoc(){
		return charLoc;
	}
	public Boolean getFull(){
		return full;
	}

}
