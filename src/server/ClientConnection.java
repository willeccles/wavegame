package server;

import java.net.*;
import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientConnection extends Thread {
	private Socket socket;
	private String address;
	private int id;
	private boolean isHost;
	private Instance instance;
	private DataInputStream in;
	private DataOutputStream out;
	private Thread outputThread;
	private ConcurrentLinkedQueue<String> outputQueue;

	public ClientConnection(int id, Socket clientSocket, Instance instance) throws IOException {
		this.id = id;
		this.socket = clientSocket;
		this.isHost = isHost;
		this.instance = instance;
		outputQueue = new ConcurrentLinkedQueue<String>();
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		outputThread = new Thread(() -> {
			String output;
			while (socket.isConnected()) {
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
	}
	
	public void run() {
		if (!outputThread.isAlive()) outputThread.start();
		// here we should wait for things to come from the client and then send them to the instance the client is in
		String input = "";
		while (socket.isConnected()) {
			try {
				input = in.readUTF();
				// deal with input here
				if (input.matches("[0-9.]+,[0-9.]+,[-0-9.]+,[-0-9.]+")) {
					// send the info about the player to the other client
					instance.sendToClient(Math.abs(this.id-1), input);
				}
				// this means the player has died
				else if (input.matches("DEAD")) {
					// tell the instance to stop the game and stuff
					instance.gameOver(Math.abs(this.id-1));
				}
			} catch (EOFException eof) {
				// this is where a client has closed the connection on its end.
				break;
			} catch (IOException ioe) {
				// in this case this means something
				ioe.printStackTrace();
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		instance.removeClient(id);
	}

	public synchronized void close() {
		try {
			in.close();
			out.close();
			socket.close();
			outputThread.join();
			this.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// sends a message to this client using the socket
	public void sendMessage(String message) {
		// send a message to the client
		try {
			outputQueue.add(message);
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}
}
