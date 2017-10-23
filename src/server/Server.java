package server;

import java.net.*;
import java.io.*;
import java.util.HashMap;

public class Server extends Thread {
	private ServerSocket serverSocket;
	public static HashMap<String, Instance> instances; // map each instance to an ID so we can manage them
	private Leaderboard lb;

	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(0);
		instances = new HashMap<String, Instance>();
		lb = new Leaderboard("leaderboard.csv");
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
				System.out.println("Listening for clients on port " + serverSocket.getLocalPort() + "...");
				Socket clientSocket = serverSocket.accept();
				DataInputStream in = new DataInputStream(clientSocket.getInputStream());
				String input = in.readUTF();

				if (input.matches("^[^|]+\\|HOST\\|[^|]+\\|[^|]+")) {
					// make a new instance with the given client as the host
					String args[] = input.split("\\|");
					String username = args[0];
					String roomname = args[2];
					String roompass = args[3];
					// TODO: handle when a roomname is already used (create new one if it's dead, if it's alive make an error of some sort happen
					instances.put(roomname, new Instance(roomname, roompass, username, clientSocket));
					instances.get(roomname).start();
				} else if (input.matches("^[^|]+\\|JOIN\\|[^|]+\\|[^|]+")) {
					// join the given instance (if it exists)
					// if it doesn't exist, send back an error message
					String args[] = input.split("\\|");
					String username = args[0];
					String roomname = args[2];
					String roompass = args[3];
					if (instances.containsKey(roomname)) {
						if (instances.get(roomname).isAlive()) {
							// join the user into the room if the password matches
							if (instances.get(roomname).checkPass(roompass)) {
								if (instances.get(roomname).getClientCount() < 2) {
									//user can join
									instances.get(roomname).joinUser(username, clientSocket);
								}
							}
						}
					}
				} else if (input.matches("NEWSCORE\\|[a-zA-Z0-9_]+,[0-9]+")) {
					String username = input.split("\\|")[1].split(",")[0];
					System.out.println(username);
					int score = Integer.parseInt(input.split(",")[1]);
					int pos = lb.insert(username, score);
					lb.print();
					DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
					out.writeUTF("POSITION:" + pos + "|SCORESLIST:" + lb.top(5));
					out.close();
					lb.saveToFile();
				}
			} catch (IOException ioe) {
				// TODO
				ioe.printStackTrace();
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
