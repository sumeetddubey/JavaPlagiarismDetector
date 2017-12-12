package algorithms.gst;

/**
 * @author sumeetdubey
 * Class defining a match in greedy string tilling. Every object denotes two indices that indicate
 * the starting indexes in the two strings where the match occurs. matchLength denotes the number of 
 * subsequent characters that are a part of this match 
 */

public class Match {
//	Starting index of this match in string 1
	private int a;
//	Starting index of this match in string 2
	private int b;
//	Length of this match
	private int matchLength;
	
	
	/**
	 * Class constructor
	 * @param a
	 * @param b
	 * @param matchLength
	 */
	public Match(int a, int b, int matchLength) {
		this.a=a;
		this.b=b;
		this.matchLength=matchLength;
	}
	
	
//	Getters
	public int getFirstStringIndex() {
		return a;
	}
	
	public int getSecondStringIndex() {
		return b;
	}
	
	public int getMatchLength() {
		return matchLength;
	}
	
	
	/**
	 * Prints out textual representation of this match
	 */
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("Index in string 1: " +getFirstStringIndex() + " \t");
		sb.append("Index in string 2: " +getSecondStringIndex() +" \t");
		sb.append("Match length: " +getMatchLength());
		return sb.toString();

	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if(o==null) {
			return false;
		}
		if(!Match.class.isAssignableFrom(o.getClass())) {
			return false;
		}
		final Match match=(Match) o;
		return(this.a==match.a && this.b==match.b && this.matchLength==match.matchLength);
	}
}
