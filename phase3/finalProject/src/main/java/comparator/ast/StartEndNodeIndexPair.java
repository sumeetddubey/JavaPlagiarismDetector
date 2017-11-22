package comparator.ast;

public class StartEndNodeIndexPair {
	private int start;
	private int end;
	
	/**
	 * Constructor 
	 * 
	 * @param start - start index of the pair
	 * @param end - end index of the pair
	 */
	public StartEndNodeIndexPair(int start, int end) {
		this.start = start;
		this.end = end;
	}

	
	/**
	 * @return - the start index of the pair
	 */
	public int getStartIndex() {
		return start;
	}
	
	/**
	 * @return - the end index of the pair
	 */
	public int getEndIndex() {
		return end;
	}
	
	/**
	 * Check if the obj is equals to this StartEndNodeIndexPair
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
	        return false;
	    }
	    if (!StartEndNodeIndexPair.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
	    final StartEndNodeIndexPair pair = (StartEndNodeIndexPair) obj;

		if(this.start == pair.getStartIndex() && this.end == pair.getEndIndex())
			return true;
		
		return false;
	}
	
	@Override
	public String toString() {
		return "[" + start + "," + end + "]"; 
	}
}
