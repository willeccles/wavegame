package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JPanel;

public class LeaderboardDisplay extends JPanel {
	private Leaderboard leaderboard;
	private Game game;
	private int x,y;
	private Image img;
	
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
		if (leaderboard.isLoading()) {
			g.drawString("Loading...", 100, 100);
		} else {
			if (leaderboard.getUserPosition() == -1) {
				g.drawString("Failed to load.", 100, 100);
				g.drawString(leaderboard.getLeaderboard(5, 0), x, 600);
				g.drawString(leaderboard.getLeaderboard(5, 1), x+515, 600);
			} else {
				for(int i = 4; i >= 0; i--) {
					if(leaderboard.getLeaderboard(i, 0) != null) {
						g.drawString(5-i + ". " + leaderboard.getLeaderboard(i, 0), x, y + (400-(100*i)));
						g.drawString(leaderboard.getLeaderboard(i, 1), x+515, y + (400-(100*i)));
					}
				}
				g.drawString(leaderboard.getUserPosition() + ". " + leaderboard.getLeaderboard(5, 0), x, y + 500);
				g.drawString(leaderboard.getLeaderboard(5, 1), x+515, y+500);
			}
		}
	}
	public void refresh() {
		repaint();
	}

}
