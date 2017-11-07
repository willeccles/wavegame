package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.lang.reflect.Array;
import javax.swing.JPanel;
import mainGame.net.LBWorker;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Leaderboard inpupt screen in the game
 * 
 * @author Kyle Gorman 10/19/17
 *
 */

public class Leaderboard extends JPanel {
	private Game game;
	private int x, y, charLoc, strX;
	private String user;
	private Boolean full;
	private HUD hud;
	private LBWorker lbworker;
	public String [][] leaderboard;
	private int loc;
	private int userpos = -1;
	private LinkedHashMap<String, Integer> scorelist = null;
	private boolean loading = true;

	public Leaderboard(Game game, HUD hud, String[][] leaderboard) {
		this.game = game;
		x = 353;
		y = 490;
		strX = x + 250;
		user = "";
		charLoc = 0;
		full = false;
		this.leaderboard = leaderboard;
		this.hud = hud;
		lbworker = new LBWorker("will.eccles.net", 25565);
		loc = 0;
	}

	public void paint (Graphics g) {
		// TODO Auto-generated method stub
		//1280x720
		Font font = new Font("Comic Sans MS", 1 , 50);
		g.setColor(Color.orange);
		g.drawRect(x, y, 566, 166);

		g.setFont(font);
		g.setColor(Color.orange);
		g.drawString("Done", x+213, y+96);

		g.setColor(Color.orange);
		g.drawRect(x, y-200, 566, 166);

		g.setFont(font);
		g.drawString(user, strX, y-100);

		if(full) {
			g.setFont(font);
			g.drawString("15 character max", x+213, y-200);
		}
	}

	public void updateUser(String temp) {
		if(charLoc == 15) {
			full = true;
		} else {
			full = false;
			if(temp.equals("back")) {
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
	}
	public String getUser() {
		return user;
	}
	public void updateLoc(int temp) {
		if(!full || temp <= 0) {
			if(temp == 0) {
				charLoc = 0;
			} else {
				charLoc = charLoc + temp;
			}
		}
	}
	public int getLoc() {
		return charLoc;
	}
	public Boolean getFull() {
		return full;
	}
	public void nextUser() {
		leaderboard[loc][0] = user;
		leaderboard[loc][1] = Integer.toString(hud.getScore()); 
		user = "";
		charLoc = 0;
		strX = x +213;
		loc++;
	}
	public String getLeaderboard(int i, int x) {
		return leaderboard[i][x];
	}

	public String getUserPosition() {
		if (userpos != -1)
			return Integer.toString(userpos);
		else
			return "  ";
	}

	public boolean isLoading() {
		return loading;
	}

	public void loadLeaderboard() {
		try {
			lbworker.exchangeInfo(user, hud.getScore());
			userpos = lbworker.getUserPosition();
			scorelist = lbworker.getScoreList();
			if (scorelist == null) {
				for (int i = 0; i < 5; i++) {
					leaderboard[i][0] = "";
					leaderboard[i][1] = "";
				}
			} else {
				int pos = 0;
				for (Map.Entry<String, Integer> entry : scorelist.entrySet()) {
					leaderboard[pos][0] = entry.getKey().toString();
					leaderboard[pos][1] = scorelist.get(entry.getKey()).toString();
					pos++;
				}
				leaderboard[5][0] = user;
				leaderboard[5][1] = Integer.toString(hud.getScore());
			}
			loading = false;
		} catch(Exception ioe) {
			for (int i = 0; i < 5; i++) {
				leaderboard[i][0] = "";
				leaderboard[i][1] = "";
			}
			loading = false;
		}
	}
}
