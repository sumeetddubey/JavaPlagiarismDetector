package comparator.ast;

/**
 * SimilarNodeListPairs 
 * 
 * @author Wenjun
 *
 */
public class PairOfNodeIndexIntervals {
	
	NodeIndexInterval firstInterval; // first interval of indexes of nodes in the pair
	NodeIndexInterval secondInterval;  // second interval of indexes of nodes in the pair
	
	/**
	 * Constructor 
	 * @param firstInteval - the first NodeIndexInterval in the pair
	 * @param secondInterval - the second NodeIndexInterval in the pair
	 */
	public PairOfNodeIndexIntervals(NodeIndexInterval firstInteval, NodeIndexInterval secondInterval) {
		this.firstInterval = firstInteval;
		this.secondInterval = secondInterval;
	}
	
	/**
	 * @return - the start index of the first NodeIndexInterval in the pair
	 */
	public int getFirstStartNodeIndex() {
		return firstInterval.getStartIndex();
	}
	
	/**
	 * @return - the end index of the first NodeIndexInterval in the pair
	 */
	public int getFirstEndNodeIndex() {
		return firstInterval.getEndIndex();
	}
	
	/**
	 * @return - the start index of the second NodeIndexInterval in the pair
	 */
	public int getSecondStartNodeIndex() {
		return secondInterval.getStartIndex();
	}
	
	/**
	 * @return - the end index of the second NodeIndexInterval in the pair
	 */
	public int getSecondEndNodeIndex() {
		return secondInterval.getEndIndex();
	}
}
