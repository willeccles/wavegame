package server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.Comparable;

/**
 * Represents a leaderboard stored in a CSV file.
 * File format: "username",score
 * @author Will Eccles
 * @version 10-10-2017
 * */
public class Leaderboard {
	List<Score> scores = new ArrayList<Score>();
	String filename;
	boolean modified = false;

	public Leaderboard(String _filename) {
		filename = _filename;
		// read in the items and add them as scores to the arraylist above
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				scores.add(new Score(line));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert a score into the list.
	 * @param username The name of the user to whom the score belongs.
	 * @param value The score itself.
	 * @return The place (NOT index) in the list where the score is located. In case of tie, will pick lower number (i.e. 2nd place instead of 3rd place, even if they are the same)
	 */
	public int insert(String username, int value) {
		Score newScore = new Score(username, value);
		scores.add(newScore);
		scores.sort(null);
		modified = true; // this way we know to write it to the file when told to

		// binary search the list
		int rval = binarySearch(scores, newScore);
		if (rval == -1) {
			return rval; // score wasn't found
		}

		// since there could be duplicates, check for those and get the latest value in the list
		while (scores.get(rval+1).equals(newScore)) {
			rval++;
		}
		
		// return the position
		return scores.size() - rval;

	}

	/**
	 * Returns the top n scores in the format u1,s1|u2,s2|etc
	 * @param num The number of scores (i.e. 10 for the top ten).
	 * @return Top n scores list.
	 */
	public String top(int num) {
		String r = "";
		int n = num;
		if (num > scores.size()) {
			n = scores.size();
		}
		for (int i = scores.size() - n; i < scores.size(); i++) {
			r += scores.get(i).username() + ",";
			r += scores.get(i).score();
			r += '|'; // end pair
		}
		return r;
	}

	/**
	 * Saves the leaderboard into the given CSV file.
	 */
	public void saveToFile() {
		if (modified) {
			try (FileWriter fw = new FileWriter(new File(filename), false)) {
				for (Score s : scores) {
					fw.write("\"" + s.username() + "\"," + s.score() + System.lineSeparator());
				}
				modified = false;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Print the whole leaderboard for testing purposes.
	 */
	public void print() {
		for (Score s : scores) {
			System.out.println(s.username() + ": " + s.score());
		}
	}

	/**
	 * Get the index in the scores list at which the score is located.
	 * @param a The list to search in.
	 * @param value The Score to look for.
	 * @return The index it was found at.
	 */
	public static int binarySearch(List<Score> a, Score value) {
		boolean found = false;
		int first = 0;
		int last = (a.size() - 1);
		int mid = (last-first)/2;
		
		while((!a.get(mid).equals(value)) && (mid < a.size() - 1)) {
			if (!a.get(mid).equals(value)) {
				mid = (first + last)/2;

				if ((a.get(mid).isGreaterThan(value)) && (mid != a.size() - 1))
				{
					found = false;
					last = (mid - 1);
				}
				else if ((a.get(mid).isLessThan(value)) && (mid != a.size() - 1)) {
					found = false;
					first = mid + 1;
				}
				else if ((!a.get(mid).equals(value)) && (mid == a.get(a.size() - 1).score())) {
					found = false;
				}
				else if (a.get(mid).equals(value)) {
					found = true;
				}	
			}
		}

		if (a.get(mid).equals(value)) {
			return mid;
		}

		if (!found) {
			return -1;
		}
		
		return mid;
	}
}

/**
 * Represents a score, containing username and score.
 * @author Will Eccles
 * @version 10-10-2017
 */
class Score implements Comparable<Score> {
	private String _username;
	private int _value;

	/**
	 * Constructor.
	 * @param username The name of the user with the score.
	 * @param score The value of the score.
	 */
	public Score(String username, int score) {
		_username = username;
		_value = score;
	}

	/**
	 * Constructor using a line from the CSV file.
	 * @param CSVLine Line from CSV file, such as "username",5
	 */
	public Score(String CSVLine) {
		String parts[] = CSVLine.split(",");
		_username = parts[0].replaceAll("((?<=^)\"|\"(?=$))","");
		_value = Integer.parseInt(parts[1]);
	}

	/**
	 * Get the score value.
	 * @return The value.
	 */
	public int score() {
		return _value;
	}

	/**
	 * Get the username associated with the score.
	 * @return The username.
	 */
	public String username() {
		return _username;
	}

	@Override
	public int compareTo(Score s) {
		if (_value < s.score()) {
			return -1;
		} else if (_value > s.score()) {
			return 1;
		} else {
			return 0;
		}
	}

	public boolean equals(Score s) {
		return (_username.equals(s.username())) && (_value == s.score());
	}

	public boolean isGreaterThan(Score s) {
		return this.compareTo(s) > 0;
	}

	public boolean isLessThan(Score s) {
		return this.compareTo(s) < 0;
	}
}
