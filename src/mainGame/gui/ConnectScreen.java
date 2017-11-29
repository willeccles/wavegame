package mainGame.gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mainGame.Game;
import mainGame.Game.STATE;
import mainGame.spawn.SpawnMultiplayer;

public class ConnectScreen extends JPanel {
	private boolean rendered = false;
	private Game game;
	private JLabel ipLabel;
	private JLabel nameLabel;
	private JLabel passwordLabel;
	private JLabel portLabel;
	private JTextField ip;
	private JTextField name;
	private JTextField password;
	private JTextField port;
	private JButton backButton;
	private JButton connectButton;
	private SpawnMultiplayer mpSpawn;

	public ConnectScreen(Game game, SpawnMultiplayer mp) {
		super();
		this.setBackground(Color.black);
		this.setPreferredSize(new java.awt.Dimension(Game.WIDTH, Game.HEIGHT));
		this.setSize(new java.awt.Dimension(Game.WIDTH, Game.HEIGHT));
		this.game = game;
		this.mpSpawn = mp;
		game.getFrame().add(this);
		this.setFocusable(true);
		ip = new JTextField();
		ip.setFocusable(true);
		ip.setBounds(200, 100, 300, 50);
		ip.setFont(ip.getFont().deriveFont(25f));
		this.add(ip);
		name = new JTextField();
		name.setFocusable(true);
		name.setBounds(200, 250, 300, 50);
		name.setFont(name.getFont().deriveFont(25f));
		this.add(name);
		password = new JTextField();
		password.setFocusable(true);
		password.setBounds(200, 400, 300, 50);
		password.setFont(password.getFont().deriveFont(25f));
		this.add(password);
		port = new JTextField();
		port.setFocusable(true);
		port.setBounds(550, 100, 100, 50);
		port.setFont(port.getFont().deriveFont(25f));
		this.add(port);
		portLabel = new JLabel();
		nameLabel = new JLabel();
		passwordLabel = new JLabel();
		ipLabel = new JLabel();
		portLabel.setForeground(Color.white);
		portLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		portLabel.setLocation(560, 20);
		portLabel.setText("Port");
		portLabel.setSize(100, 100);
		this.add(portLabel);
		ipLabel.setForeground(Color.white);
		ipLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		ipLabel.setLocation(250, 20);
		ipLabel.setText("IP Address");
		ipLabel.setSize(300, 100);
		this.add(ipLabel);
		nameLabel.setForeground(Color.white);
		nameLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		nameLabel.setLocation(240, 175);
		nameLabel.setText("Room Name");
		nameLabel.setSize(300, 100);
		this.add(nameLabel);
		passwordLabel.setForeground(Color.white);
		passwordLabel.setFont(new Font("Arial", Font.PLAIN, 40));
		passwordLabel.setLocation(205, 320);
		passwordLabel.setText("Room Password");
		passwordLabel.setSize(300, 100);
		this.add(passwordLabel);
		backButton = new JButton();
		backButton.setText("Back");
		backButton.setFont(new Font("Arial", Font.PLAIN, 40));
		backButton.setLocation(50, 600);
		backButton.setSize(400, 100);
		backButton.addActionListener((ae) -> {
			game.gameState = STATE.Menu;
		});
		this.add(backButton);
		connectButton = new JButton();
		connectButton.setText("Connect");
		connectButton.setFont(new Font("Arial", Font.PLAIN, 40));
		connectButton.setLocation(825, 600);
		connectButton.setSize(400, 100);
		connectButton.addActionListener((ae) -> {
			// because this project is for school I don't really car that much, so regexes are all "meh"
			// check the IP
			if (!ip.getText().matches("^[0-9.a-zA-Z-]+$")) {
				game.popup("Invalid IP address.");
				return;
			}
			// check the port
			if (!port.getText().matches("^\\d{1,5}$")) {
				game.popup("Invalid port.");
				return;
			}
			// check the room name
			if (!name.getText().matches("^[^|]+$")) {
				game.popup("Invalid room name (cannot contain | character).");
				return;
			}
			// check the password
			if (!password.getText().matches("^[^|]+$")) {
				game.popup("Invalid password (cannot contain | character).");
				return;
			}
			// since it all checks out, now we try to connect
			try {
				mpSpawn.createClient(ip.getText(), Integer.parseInt(port.getText()));
				if (game.gameState == Game.STATE.Host) {
					mpSpawn.getClient().host_game(name.getText(), password.getText());
				} else if (game.gameState == Game.STATE.Join) {
					mpSpawn.getClient().join_game(name.getText(), password.getText());
				}
				// otherwise this is broken
				if (game.gameState != Game.STATE.Menu)
					game.gameState = Game.STATE.Multiplayer;
			} catch (Exception e) {
				game.gameState = Game.STATE.Menu;
			}
		});
		this.add(connectButton);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
