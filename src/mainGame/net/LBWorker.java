package mainGame.net;

import java.net.*;
import java.io.*;
import java.util.HashMap;

/**
 * A class that does work related to the leaderboard, AKA sending and receiving information.
 * @author Will Eccles
 */
public class LBWorker {
	private String _address;
	private int _port;
	private HashMap<String, Integer> scores = null;
	private int userpos = -1;

	public LBWorker(String address, int port) {
		_address = address;
		_port = port;
	}

	/**
	 * Send the user's score to the server, returning a nested HashMap.
	 * @param username The user's username.
	 * @param score The user's score.
	 * @throws IOException
	 */
	public void exchangeInfo(String username, int score) throws IOException {
		Socket client = new Socket(_address, _port);

		// IO streams
		DataOutputStream out = new DataOutputStream(client.getOutputStream());
		DataInputStream in = new DataInputStream(client.getInputStream());

		// send the user's score and wait for the server response
		out.writeUTF("NEWSCORE|" + username + "," + score);
		
		String input = "";
		if (client.isConnected()) {
			input = in.readUTF();
		}

		in.close();
		out.close();
		client.close();
		
		// check input
		if (input.matches("^POSITION:[0-9]+|SCORELIST:([a-zA-Z0-9]{1,15},[0-9]+|)$")) {
			// so this means that the input is usable
			userpos = Integer.parseInt(input.split(":")[1].split("|")[0]);
			scores = new HashMap<String, Integer>();
			String rawData[] = input.split(":")[2].split("|");
			for (String datum : rawData) {
				scores.put(datum.split(",")[0], Integer.parseInt(datum.split(",")[1]));
			}
		}

	}

	/**
	 * Get the user's position on the leaderboard.
	 * @return null if exchangeInfo wasn't run or had an issue.
	 */
	public int getUserPosition() {
		return userpos;
	}

	/**
	 * Get the current top leaderboard standings.
	 * @return null if exchangeInfo didn't run or had an error.
	 */
	public HashMap<String, Integer> getScoreList() {
		return scores;
	}
}
