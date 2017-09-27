package server;

import java.net.*;
import java.io.*;

public class ClientConnection extends Thread {
	private Socket socket;
	private String username;
	private String address;
	private int id;
	private boolean isHost;
	private Instance instance;
	private DataInputStream in;
	private DataOutputStream out;

	public ClientConnection(String username, int id, Socket clientSocket, Instance instance) throws IOException {
		this.username = username;
		this.id = id;
		this.socket = clientSocket;
		this.isHost = isHost;
		this.instance = instance;
		in = new DataInputStream(clientSocket.getInputStream());
		out = new DataOutputStream(clientSocket.getOutputStream());
	}
	
	public void run() {
		// here we should wait for things to come from the client and then send them to the instance the client is in
		String input = "";
		while (socket.isConnected()) {
			try {
				input = in.readUTF();
				// TODO: deal with input here
				/* if the message is telling the server the player's current velocity vector and position, this should be relayed ASAP to the other client, whose game will then display the player's change in velocity */

				if (input.matches("^DIR=[0-9]+ POS=[0-9]+,[0-9]+")) {
					// DIR is the angle the character is moving at (0, 45, 90, etc)
					// POS is the x,y coords of the character in pixels, based on the display of 1280x720
					// the ID of the other player is abs(this.id-1)
				}
			} catch (IOException ioe) {
				// TODO
			} catch (EOFException eof) {
				// TODO
				// this is when the client has disconnected and the inputstream reaches EOF
			}
		}

		// try{}catch(){}
		// close streams, socket, this.join(), etc
		try {
			in.close();
			out.close();
			socket.close();
			this.join();
		} catch (IOException ioe) {
			// TODO
		}
	}

	// sends a message to this client using the socket
	// TODO: determine whether or not this should be synchronized
	public void sendMessage(String message) {
		// send a message to the client
		try {
			out.writeUTF(message);
		} catch (IOException ioe) {
			// TODO
		}
	}
}
