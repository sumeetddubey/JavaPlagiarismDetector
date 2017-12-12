package comparator.functionSignature;

/**
 * @author sumeetdubey
 * Class for a function match pair. The class has two objects that stores the function signatures of 
 * matching function pairs.
 */
public class FunctionMatchPair {
	
	/**
	 * Class constructor
	 * @param functionName1
	 * @param functionName2
	 */
	public FunctionMatchPair(FunctionSignature functionName1, FunctionSignature functionName2) {
		this.fs1 = functionName1;
		this.fs2 = functionName2;
	}
	
	/**
	 * Prints textual representation of a match pair
	 */
	public String textualRepresentation() {
		StringBuilder sb = new StringBuilder();
		sb.append(fs1.getName() +" from program 1 matches with " +fs2.getName() +" from program 2\n");
		return sb.toString();
	}
	
	private FunctionSignature fs1;
	private FunctionSignature fs2;
}
