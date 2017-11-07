package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class LeaderboardDisplay extends JPanel {
	private Leaderboard leaderboard;
	private Game game;
	private int x,y;
	
	public LeaderboardDisplay(Leaderboard leaderboard, Game game) {
		this.leaderboard = leaderboard;
		this.game = game;
		x = 100;
		y = 100;
	}

	public void paint (Graphics g) {
		Font font = new Font("Amoebic", 1, 50);
		Font font2 = new Font("Amoebic", 1, 40);

		g.setColor(Color.white);
		g.setFont(font);
		g.drawString("Leaderboard", 600, 50);
		for(int i = 0; i < 5; i++) {
			if(leaderboard.getLeaderboard(i, 0) != null) {
				g.drawString(leaderboard.getLeaderboard(i, 0), x, y + 100*i);
				g.drawString(leaderboard.getLeaderboard(i, 1), x+500, y + 100*i);
			}
		}
	}
	public void refresh() {
		repaint();
	}

}
