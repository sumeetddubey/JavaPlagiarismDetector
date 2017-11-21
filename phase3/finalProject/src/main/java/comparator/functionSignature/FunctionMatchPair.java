package comparator.functionSignature;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class FunctionMatchPair {
	public FunctionMatchPair(FunctionSignature functionName1, FunctionSignature functionName2) {
		this.fs1 = functionName1;
		this.fs2 = functionName2;
	}
	
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
	
	public void textualRepresentation() {
		StringBuilder sb = new StringBuilder();
		sb.append(fs1.getName());
		generateArgumentString(sb, fs1);
		sb.append(fs2.getName());
		generateArgumentString(sb, fs2);
		System.out.println(sb);
	}

	/**
	 * @param sb
	 */
	private void generateArgumentString(StringBuilder sb, FunctionSignature fs) {
		Iterator<Entry<String, Integer>> it = fs.getArgs().entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry pair=(Map.Entry) it.next();
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
