package server;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Iterator;

/**
 * Instance of a game. Manages clients and manages the game between them.
 * @author Will Eccles
 */
public class Instance extends Thread {
	private HashMap<Integer, ClientConnection> clients;
	private String roomname;
	private String roompass;
	private SurvivalSpawner spawner;
	private boolean running = false;
	
	/**
	 * Constructor for Instance.
	 * @param name The name of the room.
	 * @param pass The password for the room.
	 * @param clientSocket The socket the "host" user is connected through.
	 */
	public Instance(String name, String pass, Socket clientSocket) throws IOException {
		roomname = name;
		roompass = pass;
		clients = new HashMap<Integer, ClientConnection>();
		try {
			clients.put(0, new ClientConnection(0, clientSocket, this));
			clients.get(0).start();
			spawner = new SurvivalSpawner();
		} catch (Exception ioe) {
			// TODO
			ioe.printStackTrace();
		}
	}

	/**
	 * Overridden run method from Thread.
	 */
	public void run() {
		// first we should wait for a second person to join the room
		while (getClientCount() != 2) {
			// here we should check if the number hits 0 (aka if the host DC's during this time)
			if (clients.size() == 0) {
				try {
					System.out.println("only one user and they left");
					this.join();
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}
		// now that there are two players, we can start the game running
		running = true;
		
		if (this.isAlive()) {
			// locations for both players (y stays the same for each)
			double x1 = (1280.0/3.0)-(21.0/2.0);
			double x2 = (1280.0*2.0/3.0)-(21.0/2.0);
			double y = (720.0/2.0)-(21.0/2.0);
			sendToClient(0, String.format("START:%f,%f,%f,%f", x1, y, x2, y)); // send each player the spawn location of both players
			sendToClient(1, String.format("START:%f,%f,%f,%f", x2, y, x1, y));
			
			// make the game clock
			long lastTime = System.nanoTime();
			double amountOfTicks = 0.5; // instead of 60, we want 0.5 so it only ticks once every 2 seconds to send things to the client
			double ns = 1000000000 / amountOfTicks;
			double delta = 0;
			while (running) {
				long now = System.nanoTime();
				delta += (now - lastTime) / ns;
				lastTime = now;
				while (delta >= 1) {
					tick(); // every 2s do stuff
					delta--;
				}
			}

			// now that the loop has stopped we can kill things
			closeAndRemoveClient(0);
			closeAndRemoveClient(1);
		}
	}

	/**
	 * Stops the game loop.
	 */
	public synchronized void close() {
		running = false;
	}

	/**
	 * Run every 2s to handle game stuff.
	 */
	private void tick() {
		// handle if a user has left, kill the game (assuming the other user is connected, i suppose)
		if (getClientCount() < 2) {
			System.out.println("why is this happening to me");
			sendToAll("OTHER_LEFT"); // this means the other player has left the game
			close();
		}
		// get an entity to spawn
		Entity e = spawner.getNext();
		
		// send to each of the clients
		// msg: SPAWN:<ID ordinal>,x,y,option,side
		//sendToAll("SPAWN:" + e.getType().ordinal() + ',' + e.getX() + ',' + e.getY() + ',' + e.getOption() + ',' + e.getSide());
	}

	/**
	 * Check the given password against the password for the room.
	 * @param pass The password sent by the client.
	 * @return True if the password matches, false if not.
	 */
	public boolean checkPass(String pass) {
		return (pass.equals(roompass));
	}

	/**
	 * Get the number of clients. Probably doesn't need to be synchronized, but can't hurt.
	 * @return Number of clients.
	 */
	public synchronized int getClientCount() {
		return clients.size();
	}

	/**
	 * Closes a given client.
	 * @param id The ID of the client to close.
	 */
	private synchronized void closeAndRemoveClient(int id) {
		if (clients.containsKey(id)) {
			clients.get(id).close();
			clients.remove(id);
		}
	}

	/**
	 * Removes a client from the list of clients.
	 * @param id The ID of the client to remove.
	 */
	public synchronized void removeClient(int id) {
		if (clients.get(id) == null)
			return;
		clients.get(id).close();
		clients.remove(id);
	}


	public synchronized void gameOver(int winnerID) {
		
	}

	/**
	 * Join a user to the instance. Should be used by the Server for the most part.
	 * @param clientSocket The socket the client is connected to.
	 */
	public synchronized void joinUser(Socket clientSocket) {
		try {
			clients.put(1, new ClientConnection(1, clientSocket, this));
			clients.get(1).start();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Send a message to the client with the given ID.
	 * @param id The ID of the client.
	 * @param message The message to send to the client.
	 */
	public void sendToClient(int id, String message) {
		// get the client for the given ID and send the message to it
		if (clients.get(id) != null)
			clients.get(id).sendMessage(message);
	}

	/**
	 * Send a message to all the clients, which should just be 2.
	 * @param message Message to send to all clients.
	 */
	public void sendToAll(String message) {
		Iterator it = clients.entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry) it.next();
			if (clients.get(pair.getKey()).isAlive()) {
				sendToClient((Integer)pair.getKey(), message);
			}
			it.remove(); // avoids ConcurrentModificationException, which a for each doesn't
		}
	}
}
