package algorithms;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author sumeetdubey
 * Class for performing greedy string tilling on two strings. The algorithm finds common substrings in two
 * strings regardless of the order in which they occur in the strings.
 *
 */

public class GreedyStringTilling {
	private int minMatchLength;
	private HashSet<Match> tiles;
	private ArrayList<GSTToken> arrayA;
	private ArrayList<GSTToken> arrayB;
	
	
	/**
	 * Class constructor
	 * @param Minimum length of a substring that can be considered a match
	 * @param String a
	 * @param String b
	 */
	public GreedyStringTilling(int minMatchLength, String a, String b) {
		this.minMatchLength=minMatchLength;
		this.tiles=new HashSet<Match>();
		this.arrayA=generateTokenArray(a);
		this.arrayB=generateTokenArray(b);
	}
	
	
	/**
	 * Generates an array of token object for the given string
	 * @param A string
	 * @return Array of tokens
	 */
	private ArrayList<GSTToken> generateTokenArray(String a) {
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
	 * @return set of matches
	 */
	public HashSet<Match> GST() {
		HashSet<Match> matches = new HashSet<Match>();
		int maxMatch;
		do {
			maxMatch=minMatchLength;
			matches.clear();
//			maxMatch is updated with new max substring size
			maxMatch = findMatchingSubstrings(matches, maxMatch);
			for(Match m: matches) {
				for(int j=0; j<maxMatch; j++) {
					Mark(arrayA, m.getFirstStringIndex()+j);
					Mark(arrayB, m.getSecondStringIndex()+j);
				}
				tiles.add(m);
			}
		}while(maxMatch>minMatchLength);
		return tiles;
	}

	/**
	 * Function for finding matches in two strings of at least maxMatch size. maxMatch is updated if 
	 * a substring match of bigger size is found
	 * @param matches
	 * @param maxMatch
	 * @return maxMatch 
	 */
	private int findMatchingSubstrings(HashSet<Match> matches, int maxMatch) {
		for(int i=0; i<arrayA.size() && Unmarked(arrayA, i); i++) {
			for(int j=0; j<arrayB.size() && Unmarked(arrayB, j); j++) {
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
		return maxMatch;
	}

	/**
	 * @param index in 1st array
	 * @param index in 2nd array
	 * @param index increment
	 * @return true if both arrays have same element at index+k 
	 */
	private boolean isEqual(int i, int j, int k) {
		return i+k<arrayA.size() && j+k<arrayB.size() && arrayA.get(i+k).getValue().equals(arrayB.get(j+k).getValue()) && Unmarked(arrayA, i+k) && Unmarked(arrayB, j+k);
	}

	
	/**
	 * Marks array element at given location
	 * @param token array
	 * @param element index to mark
	 */
	private void Mark(ArrayList<GSTToken> arr, int i) {
		arr.get(i).setMarked(true);
	}

	/**
	 * @param token array
	 * @param index i
	 * @return true if element at index i in token array is not marked
	 */
	private boolean Unmarked(ArrayList<GSTToken> arr, int i) {
		return !arr.get(i).isMarked();
	}
}
