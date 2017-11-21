package comparator.functionSignature;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
	
//	Getters and Setters
	public FunctionSignature getFunctionName1() {
		return fs1;
	}

	public void setFunctionName1(FunctionSignature functionName1) {
		this.fs1 = functionName1;
	}

	public FunctionSignature getFunctionName2() {
		return fs2;
	}

	public void setFunctionName2(FunctionSignature functionName2) {
		this.fs2 = functionName2;
	}
	
	
	/**
	 * Prints textual representation of a match pair
	 */
	public void textualRepresentation() {
		StringBuilder sb = new StringBuilder();
		sb.append(fs1.getName());
		generateArgumentString(sb, fs1);
		sb.append(fs2.getName());
		generateArgumentString(sb, fs2);
		System.out.println(sb);
	}

	/**
	 * Generates a string of this function signature arguments
	 * @param sb
	 */
	private void generateArgumentString(StringBuilder sb, FunctionSignature fs) {
		Iterator<Entry<String, Integer>> it = fs.getArgs().entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, Integer> pair=(Map.Entry<String, Integer>) it.next();
			if(pair.getKey().equals("none")) {
				sb.append(" having no arguments");
			}
			else {
				sb.append(" having " + pair.getValue() + " argument of " +pair.getKey() +" type");
				
			}
			sb.append("    ");
		}
	}
	
	private FunctionSignature fs1;
	private FunctionSignature fs2;
}
