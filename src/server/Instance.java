package server;

import java.net.*;
import java.io.*;
import java.util.HashMap;

/**
 * Instance of a game. Manages clients and manages the game between them.
 * @author Will Eccles
 */
public class Instance extends Thread {
	private HashMap<int, ClientConnection> clients;
	private String roomname;
	private String roompass;
	
	public Instance(String name, String pass, String hostName, Socket clientSocket) throws IOException {
		clients = new HashMap<int, ClientConnection>;
		try {
			clients.put(0, new ClientConnection(hostName, 0, clientSocket, this));
			clients.get(0).start();
		} catch (IOException ioe) {
			// TODO
		}
	}

	public void run() {
		// first we should wait for a second person to join the room
		while (clients.size() != 2) {
			// here we should check if the number hits 0 (aka if the host DC's during this time)
			if (clients.size() == 0) {
				this.join();
			}
		}
		
		if (this.isAlive()) {}
		/* TODO: tell clients that the game is starting */
		
		/* TODO: every time the velocity (vector) of a client changes, it's change should be relayed to the other client through the server (see ClientConnection.java) */
		/* TODO: at certain intervals, tell client which kinds of enemies (AKA what level) are spawning and where they are (assuming it's not a constant based on the level), everything else (rendering, health, etc.) are all clientside */
	}

	public boolean checkPass(String pass) {
		return (pass.equals(roompass));
	}

	public synchronized int getClientCount() {
		return clients.size();
	}

	public synchronized int removeClient(int id) {
		clients.remove(id);
	}

	public void joinUser(String clientName, Socket clientSocket) {
		try {
			clients.set(1, new ClientConnection(clientName, 1, clientSocket, this));
			clients.get(1).start();
		} catch (IOException ioe) {
			// TODO
		}
	}

	// this is used to send a message to another client based on ID
	// e.g. if client 0 is doing it, it would say 1 for the id
	public void sendToClient(int id, String message) {
		// get the client for the given ID and send the message to it
		clients.get(id).sendMessage(message);
	}
}
