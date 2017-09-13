package server;

import java.net.*;
import java.io.*;
import java.util.HashMap;

public class Server extends Thread {
	private ServerSocket serverSocket;
	public static HashMap<int, Instance> instances; // map each instance to an ID so we can manage them
	private int nextInstanceID = 0; // the ID of the next instance to be created. This will not allow reuse of instances, but really the number of them should never exceed 2^32-1 anyway.

	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(0);
		instances = new HashMap<int, Instance>;
	}

	public void run() {
		while (true) {
			// server just deals with getting users and putting them where they need to be
			// the server waits for a client to connect, then waits for it to send a message
			// the message should be one of two formats:
			// <username>|HOST|<roomname>|<roompass>
			// <username>|JOIN|<roomname>|<roompass>
			// The first one registers a user as <username>, then starts a new game called <roomname> and with pass <roompass>.
			// Second one registers the user and then joins a room with a given username and password.
			// Any other message should be responded to with some sort of "bad message" thing
			// This tells the client to disconnect and try again
			try {
				System.out.println("Listening for clients on port " + port + "...");
				Socket clientSocket = serverSocket.accept();
				DataInputStream in = new DataInputStream(clientSocket.getInputStream());
				String input = in.readUTF();

				if (input.matches("^[^|]+\\|HOST\\|[^|]+\\|[^|]+")) {
					// make a new instance with the given client as the host
					String args[] = input.split("\\|");
					String username = args[0];
					String roomname = args[2];
					String roompass = args[3];
					instances.put(nextInstanceID, new Instance(roomname, roompass, username, clientSocket));
					instances.get(nextInstanceID).start();
				} else if (input.matches("^[^|]+\\|JOIN\\|[^|]+\\|[^|]+")) {
					// join the given instance (if it exists)
					// if it doesn't exist, send back an error message
				}
			} catch (IOException ioe) {
			} catch (SocketTimeoutException ste) {
			}
		}
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Please provide a port argument.");
			System.exit(1);
		}
		int port = Integer.parseInt(args[0]);
		try {
			Thread t = new Server(port);
			t.start();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
