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
	private boolean running = false;

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
			while (running && socket.isConnected()) {
				output = outputQueue.poll();
				if (output != null) {
					// send the output to the server
					try {
						out.writeUTF(output);
					} catch (Exception e) {
						// this means that, the client connection has closed
						// we don't want to do anything here, because it's later on in this file
						// see catch(SocketException) in the input thread
					}
				}
			}
		});
	}
	
	@Override
	public void run() {
		running = true;
		if (!outputThread.isAlive()) outputThread.start();
		// here we should wait for things to come from the client and then send them to the instance the client is in
		String input = "";
		while (running && socket.isConnected()) {
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
				// when the socket reaches EOF before it finishes reading
				// if this happens, it's likely an error with code, rather than a runtime-specific error
				// however, the user should still see this as "the other person left" since it definitely can happen when the user disconnects
				break;
			} catch (SocketException se) {
				// the socket is closed by the client
				// in other words, there was a client-side issue that lead to the client disconnecting
				break;
			} catch (IOException ioe) {
				break;
			} catch (Exception e) {
				break;
			}
		}
		if (running)
			instance.removeClient(id);
	}

	public synchronized void close() {
		try {
			running = false;
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
