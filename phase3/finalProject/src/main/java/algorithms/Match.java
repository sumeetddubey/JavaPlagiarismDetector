package algorithms;

/*
 Class defining a match in greedy string tilling. Every object denotes two indices that indicate
 the starting indexes in the two strings where the match occurs. matchLength denotes the number of 
 subsequent characters that are a part of this match 
 */

public class Match {
	private int a;
	private int b;
	private int matchLength;
	
	public Match(int a, int b, int matchLength) {
		this.a=a;
		this.b=b;
		this.matchLength=matchLength;
	}
	
	public int getFirstStringIndex() {
		return a;
	}
	
	public int getSecondStringIndex() {
		return b;
	}
	
	public int getMatchLength() {
		return matchLength;
	}
	
	public void textualRepresentation() {
		StringBuilder sb=new StringBuilder();
		sb.append("Index in string 1: " +getFirstStringIndex() + "\t");
		sb.append("Index in string 2: " +getSecondStringIndex() +"\t");
		sb.append("Match length: " +getMatchLength() + "\n");
		System.out.println(sb);
	}
}
