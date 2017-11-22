package comparator.ast;

/**
 * SimilarNodeListPairs 
 * 
 * @author Wenjun
 *
 */
public class SimilarNodeListPairs {
	StartEndNodeIndexPair first;
	StartEndNodeIndexPair second;
	
	public SimilarNodeListPairs(int firstStart, int firstEnd, int secondStart, int secondEnd) {
		this.first = new StartEndNodeIndexPair(firstStart, firstEnd);
		this.second = new StartEndNodeIndexPair(secondStart, secondEnd);
	}
	
	public int getFirstStartNodeIndex() {
		return first.getStartIndex();
	}
	
	public int getFirstEndNodeIndex() {
		return first.getEndIndex();
	}
	
	public int getSecondStartNodeIndex() {
		return second.getStartIndex();
	}
	
	public int getSecondEndNodeIndex() {
		return second.getEndIndex();
	}
	
	public StartEndNodeIndexPair getFirstStartEndPair() {
		return first;
	}
	
	public StartEndNodeIndexPair getSecondStartEndPair() {
		return second;
	}
}
