package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Leaderboard {
	private Game game;
	
	public Leaderboard(Game game){
		this.game = game;
	}
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		Font font = new Font("Comic Sans MS", 1 , 66);
		g.setColor(Color.white);
		g.drawRect(53, 90, 566, 166);
		g.setFont(font);
		g.drawString("Done", 266, 186);
	}

}
