package comparator.functionSignature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author sumeetdubey
 * Class for storing and working on function signatures of two programs. Signature consists of the function
 * name. its return type and the data types of arguments for that function
 */
public class FunctionSignature {
//	variables for storing function name, return type and arguments
	private String name, returnType;
	private HashMap<String, Integer> args;
	

	/**
	 * Class constructor
	 * @param function name
	 * @param list of arguments
	 * @param return type
	 */
	public FunctionSignature(String name, ArrayList<String> args, String returnType) {
		this.name=name;
		this.returnType=returnType;
		this.args=new HashMap<String, Integer>();
		constructArgumentsMap(args);
	}



	/**
	 * Populates the list of arguments with argument type and number of arguments with that type
	 * @param args
	 */
	private void constructArgumentsMap(ArrayList<String> args) {
		for(String arg: args) {
			if (this.args.containsKey(arg)){
				this.args.put(arg, this.args.get(arg)+1);
			}
			else {
				this.args.put(arg, 1);
			}
		}
	}	

	/**
	 * prints out the textual representation of this function
	 */
	public void textualRepresentation() {
		StringBuilder sb=new StringBuilder();
		sb.append("Function name: " +this.name +"\t");
		sb.append("Return type: " +this.returnType +"\t");
		sb.append("Function arguments: " +Arrays.asList(args));
		System.out.println(sb);
	}

	/**
	 * compares if two function signatures have the same return type and arguments
	 * @param function signature
	 * @return boolean
	 */
	public boolean signatureComparison(FunctionSignature fn) {
		FunctionSignature f1=this;
		FunctionSignature f2=fn;
//		check if return type and number of arguments are the same
		if(f1.returnType.equals(f2.returnType) && f1.args.size()==f2.args.size()) {
			for(String key: f1.args.keySet()) {
//				return false if 2nd function signature does not contain arguments from 1st
				if(!f2.args.containsKey(key)) {
					return false;
				}
//				return false if current arguments does not have same value
				else if(f1.args.get(key) != f2.args.get(key)) {
					return false;
				}
			}
		}
		else {
			return false; 
		}
//		return true if none of the above is true
		return true;
	}
	
//	Getters 
	public String getName() {
		return this.name;
	}
	
	public String getReturnType() {
		return this.returnType;
	}
	
	public HashMap<String, Integer> getArgs(){
		return this.args;
	}
}
