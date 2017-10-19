package mainGame.net;

import java.net.*;
import java.io.*;

public class ClientConnection {
	private String address;
	private int port;
	private Socket client;
	private DataInputStream in;
	private DataOutputStream out;
	private Thread inputThread;
	
	public ClientConnection(String address, int port) throws UnknownHostException, IOException {
		this.address = address;
		this.port = port;
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
					// TODO: handle the info
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
		} catch (IOException ioe) {
			e.printStackTrace();
		}
	}
}
