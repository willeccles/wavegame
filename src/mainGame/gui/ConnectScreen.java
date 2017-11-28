package mainGame.gui;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mainGame.Game;
import mainGame.Game.STATE;
import mainGame.Player;

public class ConnectScreen extends JPanel{
	private boolean rendered = false;
	private Game game;
	private JTextField ip;
	private JButton backButton;
	private JButton connectButton;
	
	public ConnectScreen(Game game) {
		super();
	    this.setPreferredSize(new java.awt.Dimension(game.WIDTH, game.HEIGHT));
	    this.setSize(new java.awt.Dimension(game.WIDTH, game.HEIGHT));
		this.game = game;
		game.getFrame().add(this);
		this.setFocusable(true);
        ip = new JTextField(25);
		ip.setFocusable(true);
        ip.setBounds(200, 100, 300, 50);
        ip.setFont(ip.getFont().deriveFont(25f));
        this.add(ip);
        backButton = new JButton();
        backButton.setText("Menu");
        backButton.setLocation(400, 400);
        backButton.setSize(100, 50);
        this.add(backButton);
        
        

	}
	
	public void render(Graphics g) {
		super.paintComponent(g);
		this.paintComponents(g);
		if(game.gameState == STATE.Join) {
			
		}
		else if(game.gameState == STATE.Host) {
			
		}
	}
}