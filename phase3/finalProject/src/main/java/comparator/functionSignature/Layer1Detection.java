package comparator.functionSignature;
import java.io.File;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import interfaces.IComparator;
import utility.Report;

/**
 * @author sumeetdubey
 * Class for detecting function signature similarity
 */

public class Layer1Detection implements IComparator{
//	A set that stores pairs of matching function signatures  
	private HashSet<FunctionMatchPair> matchPairs=new HashSet<FunctionMatchPair>();
	
	/* (non-Javadoc)
	 * @see comparator.hashcode.IComparator#generateReport(java.lang.String, java.lang.String)
	 */
	@Override
	public Report generateReport(File programA, File programB) {
		String strProgramA=getCanonicalName(programA);
		String strProgramB=getCanonicalName(programB);
		StringBuilder sb=new StringBuilder();
		float score=comparePrograms(strProgramA, strProgramB);
		Iterator<FunctionMatchPair> it=matchPairs.iterator();
		while(it.hasNext()) {
			sb.append(it.next().textualRepresentation());
			sb.append("\n");
		}
		Report report = new Report();
		report.setLayer("layer1");
		report.setMessage(sb.toString());
		report.setScore(score);
		return report;
	}
	
	/**
	 * Get the canonical name for this file. This will return the package name followed by file name.
	 * @param programA
	 * @return
	 */
	private String getCanonicalName(File programA) {
		String name = programA.getName();
		name=name.substring(0, name.indexOf('.'));
		name = "resource." + name;
		return name;
	}

	/**
	 * Function for computing similarity score of two classes
	 * score calculated as number of similar methods divided by total number of methods * 100
	 * @param c1 - name of class 1
	 * @param c2 - name of class 2
	 * @return a score indicating the similarity between methods of given classes
	 */
	public float comparePrograms(String c1, String c2) {
		int cnt;
		float score;
		ArrayList<FunctionSignature> fns1=getAllMethods(c1);
		ArrayList<FunctionSignature> fns2=getAllMethods(c2);
//		if block to calculate score based on the program with lesser
//		number of functions
		if(fns1.size()<fns2.size()) {
			cnt=compareProgramsHelper(fns1, fns2);
			score=cnt/fns1.size() * 100;
		}
		else {
			cnt=compareProgramsHelper(fns2, fns1);
			score=(float)cnt/(float)fns2.size() * 100;
		}
		return score;
	}
	
	/**
	 * Function for comparing each function in 2 classes
	 * @param fns1 - function signature of class 1
	 * @param fns2 - function signature of class 2
	 * @return number of functions with same signature
	 */
	private int compareProgramsHelper(ArrayList<FunctionSignature> fns1, ArrayList<FunctionSignature> fns2) {
		int cnt=0;
		for (FunctionSignature fs1: fns1) {
			boolean matchFound=false;
			for(FunctionSignature fs2: fns2) {
				if(fs1.signatureComparison(fs2)) {
					if(matchFound) {
						addToMatchPairs(fs1, fs2);
					}
					else {
						addToMatchPairs(fs1, fs2);
						matchFound=true;
						cnt++;
					}
				}
			}
		}
		return cnt;
	}
	
	/**
	 * Function for getting all function signatures of a class
	 * @param className
	 * @return list of function signatures
	 */
	private ArrayList<FunctionSignature> getAllMethods(String className) {
		ArrayList<FunctionSignature> fns=new ArrayList<FunctionSignature>();	
		try {
			Class<?> c=Class.forName(className);
			Method[] methods=c.getDeclaredMethods();
//			iterating over all methods to get their arguments and storing
//			in an array list
			for(Method m: methods) {
				FunctionSignature fs = createFunctionSignature(m);
				fns.add(fs);
			}
		}
//		will catch when a class definition is not found 
		catch(Exception e) {
			System.out.println("error is " +e);
		}
		return fns;
	}

	/**
	 * Generates a function signature for given method
	 * @param m - a method object 
	 * @return function signature
	 */
	private FunctionSignature createFunctionSignature(Method m) {
		String name;
		String returnType;
		name=m.getName();
		returnType=m.getReturnType().getSimpleName();
		ArrayList<String> params=extractFunctionArguments(m.getParameterTypes());
		FunctionSignature fs=new FunctionSignature(name, params, returnType);
		return fs;
	}

	/**
	 * Function for extracting the argument types for a method
	 * @param paramTypes - array of class objects giving the parameter types for a method
	 * @return parameter array
	 */
	private ArrayList<String> extractFunctionArguments(Class<?>[] paramTypes) {
		ArrayList<String> params = new ArrayList<String>();
		if(paramTypes.length==0) {
			params.add("none");
		}
		else {
			for(Class<?> param: paramTypes) {
				params.add(param.getSimpleName());
			}
		}
		return params;
	}

	/**
	 * Adds a pair of functions to matchPairs set that have matching signatures
	 * @param fs1 - function signature 1
	 * @param fs2 - function signature 2
	 */
	private void addToMatchPairs(FunctionSignature fs1, FunctionSignature fs2) {
		matchPairs.add(new FunctionMatchPair(fs1, fs2));
	}
	
	
	/**
	 * Get all match pairs
	 */
	public HashSet<FunctionMatchPair> getMatchPairs(){
		return matchPairs;
	}
}
