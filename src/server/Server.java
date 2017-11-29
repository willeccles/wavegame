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

	@Override
	public void run() {
		while (true) {
			try {
				Socket clientSocket = serverSocket.accept();
				clientSocket.setSoTimeout(0);
				clientSocket.setKeepAlive(true);
				DataInputStream in = new DataInputStream(clientSocket.getInputStream());
				String input = in.readUTF();

				if (input.matches("^HOST\\|[^|]+\\|[^|]+")) {
					// make a new instance with the given client as the host
					String args[] = input.split("\\|");
					String roomname = args[1];
					String roompass = args[2];
					if (instances.containsKey(roomname)) {
						if (!instances.get(roomname).hasDied()) {
							// room is in use
							DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
							out.writeUTF("ROOM_EXISTS");
							out.close();
						} else {
							// room is not in use
							instances.put(roomname, new Instance(roomname, roompass, clientSocket));
							instances.get(roomname).start();
						}
					} else {
						instances.put(roomname, new Instance(roomname, roompass, clientSocket));
						instances.get(roomname).start();
					}
				} else if (input.matches("^JOIN\\|[^|]+\\|[^|]+")) {
					// join the given instance (if it exists)
					// if it doesn't exist, send back an error message
					String args[] = input.split("\\|");
					String roomname = args[1];
					String roompass = args[2];
					if (instances.containsKey(roomname)) {
						if (!instances.get(roomname).hasDied()) {
							// join the user into the room if the password matches
							if (instances.get(roomname).checkPass(roompass)) {
								if (instances.get(roomname).getClientCount() < 2) {
									//user can join
									instances.get(roomname).joinUser(clientSocket);
								} else {
									System.out.println("room is full");
									DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
									out.writeUTF("ROOM_FULL");
									out.close();
								}
							} else {
								System.out.println("pass is wrong");
								DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
								out.writeUTF("BAD_PASS");
								out.close();
							}
						} else {
							System.out.println("room doesn't exist");
							DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
							out.writeUTF("BAD_ROOM_NAME");
							out.close();
						}
					} else {
						System.out.println("room doesn't exist 2");
						DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
						out.writeUTF("BAD_ROOM_NAME");
						out.close();
					}
				} else if (input.matches("NEWSCORE\\|[a-zA-Z0-9_]+,[0-9]+")) {
					String username = input.split("\\|")[1].split(",")[0];
					int score = Integer.parseInt(input.split(",")[1]);
					int pos = lb.insert(username, score);
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
