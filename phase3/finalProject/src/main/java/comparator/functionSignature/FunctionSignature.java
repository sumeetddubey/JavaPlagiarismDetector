package comparator.functionSignature;

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
	 * @param name - function name
	 * @param args - list of arguments
	 * @param returnType - function return type
	 */
	public FunctionSignature(String name, String[] args, String returnType) {
		this.name=name;
		this.returnType=returnType;
		this.args=new HashMap<String, Integer>();
		constructArgumentsMap(args);
	}

	/**
	 * Populates the list of arguments with argument type and number of arguments with that type
	 * @param args 
	 */
	private void constructArgumentsMap(String[] args) {
		for(String arg: args) {
			arg=arg.trim();
			if (this.args.containsKey(arg)){
				this.args.put(arg, this.args.get(arg)+1);
			}
			else {
				this.args.put(arg, 1);
			}
		}
	}	

	/**
	 * Compares if two function signatures have the same return type and arguments
	 * @param fn - function signature
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
