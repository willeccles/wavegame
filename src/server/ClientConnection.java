package server;

import java.net.*;
import java.io.*;

public class ClientConnection extends Thread {
	private Socket socket;
	private String address;
	private int id;
	private boolean isHost;
	private Instance instance;
	private DataInputStream in;
	private DataOutputStream out;

	public ClientConnection(int id, Socket clientSocket, Instance instance) throws IOException {
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

				if (input.matches("[0-9.]+,[0-9.]+,[0-9.]+,[0-9.]+")) {
					// send the info about the player to the other client
					instance.sendToClient(Math.abs(this.id-1), input);
				}
			} catch (IOException ioe) {
				break;
			}
		}
		instance.removeClient(id);
	}

	public synchronized void close() {
		try {
			in.close();
			out.close();
			socket.close();
			this.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// sends a message to this client using the socket
	// TODO: determine whether or not this should be synchronized
	public void sendMessage(String message) {
		// send a message to the client
		try {
			out.writeUTF(message);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
