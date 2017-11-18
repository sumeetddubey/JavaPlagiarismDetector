package comparator.functionSignature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FunctionSignature {
//	variables for storing function name, return type and arguments
	private String name, returnType;
	private HashMap<String, Integer> args;
	
//	constructor for function signature
	public FunctionSignature(String name, ArrayList<String> args, String returnType) {
		this.name=name;
		this.returnType=returnType;
		this.args=new HashMap<String, Integer>();
		for(String arg: args) {
			if (this.args.containsKey(arg)){
				this.args.put(arg, this.args.get(arg)+1);
			}
			else {
				this.args.put(arg, 1);
			}
		}
	}
	
//	prints out the textual representation of this function
	public void textualRepresentation() {
		StringBuilder sb=new StringBuilder();
		sb.append("Function name: " +this.name +"\t");
		sb.append("Return type: " +this.returnType +"\t");
		sb.append("Function arguments: " +Arrays.asList(args));
		System.out.println(sb);
	}
	
//	compares if two function signatures have the same return type and arguments
	public boolean SignatureComparison(FunctionSignature fn) {
		FunctionSignature f1=this;
		FunctionSignature f2=fn;
		if(f1==null || f2==null) {
			return false;
		}
		else {
//			check if return type and number of arguments are the same
			if(f1.returnType.equals(f2.returnType) && f1.args.size()==f2.args.size()) {
				for(String key: f1.args.keySet()) {
//					return false if 2nd function signature does not contain arguments from 1st
					if(!f2.args.containsKey(key)) {
						return false;
					}
//					return false if current arguments does not have same value
					else if(f1.args.get(key) != f2.args.get(key)) {
						return false;
					}
				}
			}
			else {
				return false;
			}
		}
//		return true if none of the above is true
		return true;
	}
	
//	getters for function signature
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
