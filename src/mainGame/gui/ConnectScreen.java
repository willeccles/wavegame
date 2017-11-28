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
	private JTextField name;
	private JTextField password;
	private JTextField port;
	private JButton backButton;
	private JButton connectButton;
	
	public ConnectScreen(Game game) {
		super();
	    this.setPreferredSize(new java.awt.Dimension(game.WIDTH, game.HEIGHT));
	    this.setSize(new java.awt.Dimension(game.WIDTH, game.HEIGHT));
		this.game = game;
		game.getFrame().add(this);
		this.setFocusable(true);
        ip = new JTextField();
		ip.setFocusable(true);
        ip.setBounds(200, 100, 300, 50);
        ip.setFont(ip.getFont().deriveFont(25f));
        this.add(ip);
        name = new JTextField();
		name.setFocusable(true);
        name.setBounds(200, 200, 300, 50);
        name.setFont(name.getFont().deriveFont(25f));
        this.add(name);
        password = new JTextField();
		password.setFocusable(true);
        password.setBounds(200, 300, 300, 50);
        password.setFont(password.getFont().deriveFont(25f));
        this.add(password);
        port = new JTextField();
		port.setFocusable(true);
        port.setBounds(550, 100, 100, 50);
        port.setFont(port.getFont().deriveFont(25f));
        this.add(port);
        backButton = new JButton();
        backButton.setText("Menu");
        backButton.setLocation(400, 400);
        backButton.setSize(200, 50);
        backButton.addActionListener((ae) -> {
        	game.gameState = STATE.Menu;
        });
        this.add(backButton);
        
        

	}
	
	public void render(Graphics g) {
		if(game.gameState == STATE.Join) {
			
		}
		else if(game.gameState == STATE.Host) {
			
		}
		super.paintComponent(g);
		this.paintComponents(g);
	}
}