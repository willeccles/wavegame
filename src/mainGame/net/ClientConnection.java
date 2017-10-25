package mainGame.net;

import java.net.*;
import java.io.*;
import mainGame.*;

public class ClientConnection {
	private String address;
	private int port;
	private Socket client;
	private DataInputStream in;
	private DataOutputStream out;
	private Thread inputThread;
	private SpawnMultiplayer spawner;
	private Player opponent;

	public ClientConnection(String address, int port, SpawnMultiplayer spawn, Player op) throws UnknownHostException, IOException {
		this.address = address;
		this.port = port;
		this.spawner = spawn;
		this.opponent = op;
		client = new Socket(address, port);
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
					else if (input.matches("SPAWN:[\\d]+,[\\d.]+,[\\d.]+,\\d,(left|right|top|bottom|)")) {
						// tell the spawner to spawn the thing
						String parts[] = input.replace("SPAWN:", "").split(",");
						ID type = ID.values()[Integer.parseInt(parts[0])];
						double x = Double.parseDouble(parts[1]);
						double y = Double.parseDouble(parts[2]);
						int option = Integer.parseInt(parts[3]);
						String side = parts[4];
						spawner.spawnEntity(type, x, y, option, side);
					}

					// TODO: handle message where room already exists when trying to host
					// TODO: handle message where room doesn't exist when trying to join
					// TODO: handle wrong password
					// TODO: handle full lobby
				} catch(IOException ioe) {
					// this means the server closed the connection (or there was some sort of DC problem)
					// TODO: this should send the user back to the menu and show an alert box or something

					try {
						client.close();
					} catch (IOException e) {
						// this means there was an issue closing the client socket
					}
				} finally {
					this.close();
					break;
				}
			}
		});

		inputThread.start();
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
	public void host(String roomname, String password) {
		// send the message to the server that you are starting a new lobby "^[^|]+\\|HOST\\|[^|]+\\|[^|]+"
		writeOut("HOST|" + roomname + "|" + password);
	}

	/**
	 * Used to join a lobby.
	 * @param roomname The name of the lobby.
	 * @param password The password for the lobby.
	 */
	public void join(String roomname, String password) {
		writeOut("JOIN|" + roomname + "|" + password);
	}

	/**
	 * Helper method for out.writeUTF() that just keeps me from having to put a try/catch on everything I do.
	 * @param msg The message to send to the thing.
	 */
	private void writeOut(String msg) {
		try {
			out.writeUTF(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
