package algorithms.gst;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author sumeetdubey
 * Class for performing greedy string tilling on two strings. The algorithm finds common substrings in two
 * strings regardless of the order in which they occur in the strings.
 *
 */

public class GreedyStringTilling {
//	Minimum length for a match to be valid
	private final int MIN_MATCH_LENGTH;
//	Set of matches that are valid
	private HashSet<Match> tiles;
//	Array of GST tokens for string A
	private ArrayList<GSTToken> arrayA;
//	Array of GST tokens for string B
	private ArrayList<GSTToken> arrayB;
	
	
	/**
	 * Class constructor
	 * @param minMatchLength - minimum length substring that should be considered as a legal match
	 */
	public GreedyStringTilling(int minMatchLength) {
		this.MIN_MATCH_LENGTH=minMatchLength;
	}
	
	/**
	 * Function for running GST on two strings a and b
	 * @param a - String a
	 * @param b - String b
	 */
	public HashSet<Match> GST(String a, String b) {
		tiles=new HashSet<Match>();
		if(a==null || b==null) {
			return tiles;
		}
		arrayA=generateTokenArray(a);
		arrayB=generateTokenArray(b);
		GST_Helper();
		return tiles;
	}
	
	/**
	 * Generates an array of token object for the given string
	 * @param a - A string
	 * @return Array of tokens
	 */
	private static ArrayList<GSTToken> generateTokenArray(String a) {
		ArrayList<GSTToken> tokenArray = new ArrayList<GSTToken>();
		GSTToken token;
		boolean marked=false;
		for(int i=0; i<a.length(); i++) {
			token = new GSTToken(Character.toString(a.charAt(i)), marked, i);
			tokenArray.add(token);
		}
		return tokenArray;
	}

	/**
	 * Generates a set of matches of common substrings in two strings. A match consists of the starting 
	 * indices of start of matching sequences in both strings and the length of match
	 * @return Set of matches
	 */
	private void GST_Helper() {
		HashSet<Match> matches = new HashSet<Match>();
		int maxMatch;
		do {
			maxMatch=MIN_MATCH_LENGTH;
			matches.clear();
//			maxMatch is updated with new max substring size
			maxMatch = findMatchingSubstrings(matches, maxMatch);
			for(Match m: matches) {
				for(int j=0; j<m.getMatchLength(); j++) {
					Mark(arrayA, m.getFirstStringIndex()+j);
					Mark(arrayB, m.getSecondStringIndex()+j);
				}
				tiles.add(m);
			}
		}while(maxMatch>MIN_MATCH_LENGTH);
	}

	/**
	 * Function for finding matches in two strings of at least maxMatch size. maxMatch is updated if 
	 * a substring match of bigger size is found
	 * @param matches - Set of matches
	 * @param maxMatch - Current value of maxMatch
	 * @return maxMatch 
	 */
	private int findMatchingSubstrings(HashSet<Match> matches, int maxMatch) {
		for(int i=0; i<arrayA.size(); i++) {
			if(Unmarked(arrayA, i)) {
				for(int j=0; j<arrayB.size(); j++) {
					if(Unmarked(arrayB, j)) {
						int k=0;
						while(isEqual(i, j, k)) {
							k++;
						}
						if(k==maxMatch) {
							matches.add(new Match(i, j, k));
						}
						else if(k>maxMatch) {
							matches.clear();
							matches.add(new Match(i, j, k));
							maxMatch=k;
						}
					}
					
				}
			}
			
		}
		return maxMatch;
	}

	/**
	 * @param i - Index in string array 1
	 * @param j - Index in string array 2
	 * @param k - Current increment offset
	 * @return true if both arrays have same element at index+k 
	 */
	private boolean isEqual(int i, int j, int k) {
		return i+k<arrayA.size() && j+k<arrayB.size() && arrayA.get(i+k).getValue().equals(arrayB.get(j+k).getValue()) && Unmarked(arrayA, i+k) && Unmarked(arrayB, j+k);
	}

	
	/**
	 * Marks array element at given location
	 * @param arr - Token array
	 * @param i - Index i in token array
	 */
	private void Mark(ArrayList<GSTToken> arr, int i) {
		arr.get(i).setMarked(true);
	}

	/**
	 * @param arr - Token array
	 * @param i - Index i in token array
	 * @return true if element at index i in token array is not marked
	 */
	private boolean Unmarked(ArrayList<GSTToken> arr, int i) {
		return !arr.get(i).isMarked();
	}
}
