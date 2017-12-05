package comparator.functionSignature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import interfaces.IComparator;
import utility.Report;
import utility.Report.ComparisonLayer;

/**
 * @author sumeetdubey
 * Class for detecting function signature similarity
 */

public class FunctionSignatureComparator implements IComparator{
//	A set that stores pairs of matching function signatures  
	private HashSet<FunctionMatchPair> matchPairs=new HashSet<FunctionMatchPair>();
	
//	a set of java keywords that will be useful for detecting constructor declaration
	private HashSet<String> javaKeywords = new HashSet<String>();
		
//	constant that defines the return type of a constructor
	private final String CLASS_OBJECT="ClassObject";
	
	
	/**
	 * Class Constructor
	 */
	public FunctionSignatureComparator() {
//		adding common java keywords to set 
		javaKeywords.add("public");
		javaKeywords.add("private");
		javaKeywords.add("protected");
		javaKeywords.add("static");
		javaKeywords.add("final");
	}

	
	/* (non-Javadoc)
	 * @see comparator.hashcode.IComparator#generateReport(java.lang.String, java.lang.String)
	 */
	@Override
	public Report generateReport(String programA, String programB){
		StringBuilder sb=new StringBuilder();
		float score=comparePrograms(programA, programB);
		if(matchPairs.size()==0) {
			sb.append("No functions with matching signatures");
		}
		else {
			generateFunctionMatchMessageString(sb, matchPairs.iterator());
		}
		Report report = new Report(ComparisonLayer.FUNCTION_SIGNATURE, score, sb.toString());
		return report;
	}


	/**
	 * Function that generates a string representation of function signatures matching for this
	 * function match pair
	 * @param sb
	 * @param it
	 */
	private void generateFunctionMatchMessageString(StringBuilder sb, Iterator<FunctionMatchPair> it) {
		while(it.hasNext()) {
			sb.append(it.next().textualRepresentation());
		}
	}

	/**
	 * Function for computing similarity score of two classes
	 * score calculated as number of similar methods divided by total number of methods * 100
	 * The total number of methods corresponds to the program that has lesser number of methods amongst
	 * the 2
	 * @param programA - program 1 as a string
	 * @param programB - program 2 as a string
	 * @return a score indicating the similarity between methods of given classes
	 */
	public float comparePrograms(String programA, String programB){
		float cnt;
		float score;
		/*
		 * boolean variable to keep track if the programs are reversed when calling compareProgramsHelper.
		 * this variable is needed so that the order in which the match pairs are created is preserved 
		 * according to the order in which the programs were uploaded
		 */
		boolean reversed;
//		return score as 0 if the string are either empty or null
		if(programA == null || programB == null || programA.length()==0 || programB.length()==0) {
			return 0;
		}
		ArrayList<FunctionSignature> fns1=getAllMethods(programA);
		ArrayList<FunctionSignature> fns2=getAllMethods(programB);
//		if block to calculate score based on the program with lesser
//		number of functions
		if(fns1.size()==0 || fns2.size()==0) {
			return 0;
		}
		if(fns1.size()<=fns2.size()) {
			reversed=false;
			cnt=compareProgramsHelper(fns1, fns2, reversed);
			score=cnt/fns1.size() * 100;
		}
		else {
			reversed=true;
			cnt=compareProgramsHelper(fns2, fns1, reversed);
			score=cnt/fns2.size() * 100;
		}
		return score;
	}
	
	/**
	 * Function for comparing each function in 2 classes
	 * @param fns1 - function signature of class 1
	 * @param fns2 - function signature of class 2
	 * @return number of functions with same signature
	 */
	private int compareProgramsHelper(ArrayList<FunctionSignature> fns1, ArrayList<FunctionSignature> fns2, boolean reversed) {
		int cnt=0;
		for (FunctionSignature fs1: fns1) {
			/*
			 * this boolean ensures that multiple matches for the same function does not influence the score
			 * but they still are added to match pairs so that they can be a part of the final report 
			 */		
			boolean matchFound=false;
			for(FunctionSignature fs2: fns2) {
				if(fs1.signatureComparison(fs2)) {
					if(matchFound) {
						if(reversed)
//							preserving order of matches as fs2 here is the 1st program
							addToMatchPairs(fs2, fs1);
						else
							addToMatchPairs(fs1, fs2);
					}
					else {
						if(reversed)
							addToMatchPairs(fs2, fs1);
						else
							addToMatchPairs(fs1, fs2);
						matchFound=true;
						cnt+=1;
					}
				}
			}
		}
		return cnt;
	}
	
	/**
	 * Function for getting all function signatures of a class
	 * @param program
	 * @return list of function signatures
	 */
	private ArrayList<FunctionSignature> getAllMethods(String program){
		ArrayList<FunctionSignature> fns=new ArrayList<FunctionSignature>();	
		/*
		 * The below regex is used for matching a line in a string that has a function declaration
		 */
		String functionDeclarationRegex="(static*\\s+)*(final*\\s+)*(public|protected|private|\\s+)*(final*\\s+)*(static*\\s+)*(final*\\s+)*+[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])";
		Scanner scanner = new Scanner(program);
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			line=line.trim();
			if(line.matches(functionDeclarationRegex)) {	
				String functionName=extractFunctionName(line);
				String returnType=extractReturnType(line, functionName);
				String args[]=extractArguments(line);
				FunctionSignature fs=new FunctionSignature(functionName, args, returnType);
				fns.add(fs);
			}
		}
		scanner.close();
		return fns;
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
	 * Function to get a function name from a string that matches a function declaration
	 * @param line
	 * @return name of this function
	 */
	private String extractFunctionName(String line) {
		String functionName="";
//		regex for matching a string that contains a function name declaration
		String functionNameRegex="(\\w+)\\s*\\(";
		Pattern pattern = Pattern.compile(functionNameRegex);
		Matcher matcher = pattern.matcher(line);
		matcher.find();
		functionName=matcher.group(1);
		return functionName;
	}

	/**
	 * Function to find all arguments in a string that matches a function declaration
	 * @param line
	 * @return Array of arguments 
	 */
	private String[] extractArguments(String line) {
		int start=line.indexOf("(") + 1;
		int end=line.lastIndexOf(")");
		String argsStr=line.substring(start, end);
		String [] args=argsStr.split(",");
		if(args[0].equals("")) {
			args= new String[] {"none"};
		}
		for(int i=0; i<args.length; i++) {
			args[i]=args[i].trim();
			args[i]=args[i].split(" ")[0];
		}
		return args;	
	}
	
	/**
	 * Function to find a return type in a String that matches a function declaration
	 * @param line
	 * @param functionName
	 * @return return type of this function
	 */
	private String extractReturnType(String line, String functionName) {
		String returnType="";
		String[] lineArr=line.split(" ");
		for(int i=0; i<lineArr.length; i++) {
			if(lineArr[i].contains(functionName)) {
				returnType=lineArr[i-1];
			}
		}
		if(javaKeywords.contains(returnType))
			returnType=CLASS_OBJECT;
		return returnType;
	}
}
