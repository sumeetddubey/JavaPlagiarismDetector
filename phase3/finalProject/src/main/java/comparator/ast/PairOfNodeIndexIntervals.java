package comparator.ast;

/**
 * SimilarNodeListPairs 
 * 
 * @author Wenjun
 *
 */
public class PairOfNodeIndexIntervals {
	
	NodeIndexInterval firstInteval; // first interval of indexes of nodes in the pair
	NodeIndexInterval secondInterval;  // second interval of indexes of nodes in the pair
	
	/**
	 * Constructor 
	 * @param firstInteval - the first NodeIndexInterval in the pair
	 * @param secondInterval - the second NodeIndexInterval in the pair
	 */
	public PairOfNodeIndexIntervals(NodeIndexInterval firstInteval, NodeIndexInterval secondInterval) {
		this.firstInteval = firstInteval;
		this.secondInterval = secondInterval;
	}
	
	/**
	 * @return - the start index of the first NodeIndexInterval in the pair
	 */
	public int getFirstStartNodeIndex() {
		return firstInteval.getStartIndex();
	}
	
	/**
	 * @return - the end index of the first NodeIndexInterval in the pair
	 */
	public int getFirstEndNodeIndex() {
		return firstInteval.getEndIndex();
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
	
	/**
	 * @return - the first NodeIndexInterval in the pair
	 */
	public NodeIndexInterval getFirstStartEndPair() {
		return firstInteval;
	}
	
	/**
	 * @return - the second NodeIndexInterval in the pair
	 */
	public NodeIndexInterval getSecondStartEndPair() {
		return secondInterval;
	}
}
