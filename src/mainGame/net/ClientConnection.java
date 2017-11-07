package mainGame.net;

import java.net.*;
import java.io.*;
import mainGame.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientConnection {
	private String address;
	private int port;
	private Socket client;
	private DataInputStream in;
	private DataOutputStream out;
	private Thread inputThread;
	private Thread outputThread;
	private SpawnMultiplayer spawner;
	private Player opponent;
	private Game game;
	private ConcurrentLinkedQueue<String> outputQueue;

	public ClientConnection(String address, int port, SpawnMultiplayer spawn, Player op, Game g) throws UnknownHostException, IOException {
		this.address = address;
		this.port = port;
		this.spawner = spawn;
		this.opponent = op;
		this.game = g;
		outputQueue = new ConcurrentLinkedQueue<String>();
		client = new Socket(address, port);
		client.setSoTimeout(0); // a read() call will block forever
		client.setKeepAlive(true);
		in = new DataInputStream(client.getInputStream());
		out = new DataOutputStream(client.getOutputStream());

		// start a thread to listen for input
		inputThread = new Thread(() -> {
			// wait for input and do stuff with it
			String input;
			while (client.isConnected()) {
				try {
					// this is where we get the input and stuff
					input = in.readUTF();

					// the command to start the game
					if (input.matches("START:[\\d.]+,[\\d.]+,[\\d.]+,[\\d.]+")) {
						// the first pair of doubles is the local player's starting location
						// the second pair is the opponent's starting location
						String parts[] = input.replace("START:", "").split(",");
						spawn.startPlaying(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
					}
					// if the input is to spawn an enemy
					else if (input.matches("SPAWN:[\\d]+,[\\d.]+,[\\d.]+,\\d,(left|right|top|bottom)")) {
						// tell the spawner to spawn the thing
						String parts[] = input.replace("SPAWN:", "").split(",");
						ID type = ID.values()[Integer.parseInt(parts[0])];
						double x = Double.parseDouble(parts[1]);
						double y = Double.parseDouble(parts[2]);
						int option = Integer.parseInt(parts[3]);
						String side = parts[4];
						spawner.spawnEntity(type, x, y, option, side);
					}
					// when the input is giving info about the other player
					else if (input.matches("[0-9.]+,[0-9.]+,[-0-9.]+,[-0-9.]+")) {
						String parts[] = input.split(",");
						opponent.setX((int)Double.parseDouble(parts[0]));
						opponent.setY((int)Double.parseDouble(parts[1]));
						opponent.setVelX((int)Double.parseDouble(parts[2]));
						opponent.setVelY((int)Double.parseDouble(parts[3]));
					}
					// when the other user leaves
					else if (input.matches("OTHER_LEFT")) {
						break;
					}

					// TODO: handle message where room already exists when trying to host
					// TODO: handle message where room doesn't exist when trying to join
					// TODO: handle wrong password
					// TODO: handle full lobby
				} catch (EOFException eof) {
					// this means that the server closed the connection
					System.out.println("Sending back to menu, server killed the connection.");
					game.gameState = Game.STATE.Menu;
					break;
				} catch(IOException ioe) {
					ioe.printStackTrace();
					try {
						client.close();
					} catch (IOException e) {
						// this means there was an issue closing the client socket
					}
				} catch (Exception e) {
					e.printStackTrace(); // catch any error that isn't caught above
				}
			}
			// after break, close
			game.gameState = Game.STATE.Menu;
			System.out.println("line 96");
			this.close();
		});

		// make a thread that sends output to the server
		outputThread = new Thread(() -> {
			String output;
			while (client.isConnected()) {
				output = outputQueue.poll();
				if (output != null) {
					// send the output to the server
					try {
						out.writeUTF(output);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		inputThread.start();
		outputThread.start();
	}

	/**
	 * Send the server updated info on the given player.
	 * @param p The player to send info on.
	 */
	public void sendPos(Player p) {
		double x = p.getX();
		double y = p.getY();
		double velX = p.getVelX();
		double velY = p.getVelY();
		writeOut(x + "," + y + "," + velX + "," + velY);
	}
	
	/**
	 * Used to host a new lobby.
	 * @param roomname The name of the lobby
	 * @param password The password on the lobby
	 */
	public void host_game(String roomname, String password) {
		writeOut("HOST|" + roomname + "|" + password);
	}

	/**
	 * Used to join a lobby.
	 * @param roomname The name of the lobby.
	 * @param password The password for the lobby.
	 */
	public void join_game(String roomname, String password) {
		writeOut("JOIN|" + roomname + "|" + password);
	}

	public void dead() {
		writeOut("DEAD");
	}

	/**
	 * Helper method for out.writeUTF() that just keeps me from having to put a try/catch on everything I do.
	 * @param msg The message to send to the thing.
	 */
	private void writeOut(String msg) {
		outputQueue.add(msg);
	}

	/**
	 * Close the connection and kill the thread(s).
	 */
	public void close() {
		try {
			in.close();
			out.close();
			client.close();
			if (inputThread.isAlive()) {
				inputThread.join();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
