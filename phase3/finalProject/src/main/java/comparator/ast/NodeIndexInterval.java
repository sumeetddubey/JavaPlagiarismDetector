package comparator.ast;

/**
 * NodeIndexInterval represents an interval of indexes
 * 
 * @author Wenjun
 *
 */
public class NodeIndexInterval {
	private int startIndex; // start index of the interval
	private int endIndex; // end index of the interval
	
	/**
	 * Constructor 
	 * 
	 * @param start - start index of the pair
	 * @param end - end index of the pair
	 */
	public NodeIndexInterval(int start, int end) {
		this.startIndex = start;
		this.endIndex = end;
	}

	
	/**
	 * @return - the start index of the pair
	 */
	public int getStartIndex() {
		return startIndex;
	}
	
	/**
	 * @return - the end index of the pair
	 */
	public int getEndIndex() {
		return endIndex;
	}
}
