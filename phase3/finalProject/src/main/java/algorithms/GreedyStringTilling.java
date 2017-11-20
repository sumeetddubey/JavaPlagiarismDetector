package algorithms;

import java.util.ArrayList;
import java.util.HashSet;

public class GreedyStringTilling {
	private int minMatchLength;
	private HashSet<Match> tiles;
	private ArrayList<GSTToken> arrayA;
	private ArrayList<GSTToken> arrayB;
	
	public GreedyStringTilling(int minMatchLength, String a, String b) {
		this.minMatchLength=minMatchLength;
		this.tiles=new HashSet<Match>();
		this.arrayA=generateTokenArray(a);
		this.arrayB=generateTokenArray(b);
	}
	
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

	public HashSet<Match> GST() {
		HashSet<Match> matches = new HashSet<Match>();
		int maxMatch;
		do {
			maxMatch=minMatchLength;
			matches.clear();
			for(int i=0; i<arrayA.size() && Unmarked(arrayA, i); i++) {
				for(int j=0; j<arrayB.size() && Unmarked(arrayB, j); j++) {
					int k=0;
					while(i+k<arrayA.size() && j+k<arrayB.size() && arrayA.get(i+k).getValue().equals(arrayB.get(j+k).getValue()) && Unmarked(arrayA, i+k) && Unmarked(arrayB, j+k)) {
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

	private void Mark(ArrayList<GSTToken> arr, int i) {
		arr.get(i).setMarked(true);
	}

	private boolean Unmarked(ArrayList<GSTToken> arr, int i) {
		return !arr.get(i).isMarked();
	}
}
