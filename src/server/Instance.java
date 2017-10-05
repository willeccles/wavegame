package server;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Instance of a game. Manages clients and manages the game between them.
 * @author Will Eccles
 */
public class Instance extends Thread {
	private ArrayList<ClientConnection> clients;
	private String roomname;
	private String roompass;
	
	public Instance(String name, String pass, String hostName, Socket hostSocket) throws IOException {
		clients = new ArrayList<ClientConnection>;
		try {
			clients.set(0, new ClientConnection(hostName, 0, hostSocket, this));
			clients.get(0).start();
		} catch (IOException ioe) {
		}
	}

	public void run() {
		// at a rate of 60Hz, update every client with the info they need to play the game
		// this should include enemies (referenced by ID, type (enum), info about it, etc.), players, times, health values, etc.
		// so here there should be a loop happening every 1/60 seconds, probably will just copy the game loop from the actual game
	}
}
