package comparator.functionSignature;
import java.io.BufferedReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import interfaces.IComparator;
import utility.Report;

/**
 * @author sumeetdubey
 * Class for detecting function signature similarity
 */

public class Layer1Detection implements IComparator{
//	A set that stores pairs of matching function signatures  
	private HashSet<FunctionMatchPair> matchPairs=new HashSet<FunctionMatchPair>();
	
//	a set of java keywords that will be useful for detecting constructor declaration
	private HashSet<String> javaKeywords = new HashSet<String>();
	
//	error logger
	private final static Logger LOGGER = Logger.getLogger(Layer1Detection.class.getName());
	
//	constant that defines the return type of a constructor
	private final String CLASS_OBJECT="ClassObject";
	
	
	/**
	 * Class Constructor
	 */
	public Layer1Detection() {
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
	public Report generateReport(File programA, File programB) {
//		String strProgramA=getCanonicalName(programA);
//		String strProgramB=getCanonicalName(programB);
		StringBuilder sb=new StringBuilder();
		float score=comparePrograms(programA, programB);
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
	 * Function for computing similarity score of two classes
	 * score calculated as number of similar methods divided by total number of methods * 100
	 * @param c1 - name of class 1
	 * @param c2 - name of class 2
	 * @return a score indicating the similarity between methods of given classes
	 */
	public float comparePrograms(File c1, File c2) {
		float cnt;
		float score;
		ArrayList<FunctionSignature> fns1=getAllMethods(c1);
		ArrayList<FunctionSignature> fns2=getAllMethods(c2);
//		if block to calculate score based on the program with lesser
//		number of functions
		if(fns1.size()==0 || fns2.size()==0) {
			return 0;
		}
		if(fns1.size()<fns2.size()) {
			cnt=compareProgramsHelper(fns1, fns2);
			score=cnt/fns1.size() * 100;
		}
		else {
			cnt=compareProgramsHelper(fns2, fns1);
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
						cnt+=1;
					}
				}
			}
		}
		return cnt;
	}


//	/**
//	 * Compares two function signatures and keeps track if a match is found 
//	 * @param cnt
//	 * @param fs1
//	 * @param fs2
//	 * @param matchFound
//	 * @return The number of functions in program 1 that have a match 
//	 */
//	private int compareSignatures(int cnt, FunctionSignature fs1, FunctionSignature fs2, boolean matchFound) {
//		if(fs1.signatureComparison(fs2)) {
//			if(matchFound) {
//				addToMatchPairs(fs1, fs2);
//			}
//			else {
//				addToMatchPairs(fs1, fs2);
//				matchFound=true;
//				cnt+=1;
//			}
//		}
//		return cnt;
//	}
	
	/**
	 * Function for getting all function signatures of a class
	 * @param classFile
	 * @return list of function signatures
	 */
	private ArrayList<FunctionSignature> getAllMethods(File classFile) {
		ArrayList<FunctionSignature> fns=new ArrayList<FunctionSignature>();	
		String functionDeclarationRegex="(static*\\s+)*(final*\\s+)*(public|protected|private|\\s+)*(final*\\s+)*(static*\\s+)*(final*\\s+)*+[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])";
		try {
			BufferedReader br=new BufferedReader(new FileReader(classFile));
			String line;
			while((line = br.readLine()) != null) {
				line=line.trim();
				if(line.matches(functionDeclarationRegex)) {	
					String functionName=extractFunctionName(line);
					String returnType=extractReturnType(line, functionName);
					String args[]=extractArguments(line);
					FunctionSignature fs=new FunctionSignature(functionName, args, returnType);
					fs.textualRepresentation();
					fns.add(fs);
				}
			}
			System.out.println("\n");
			br.close();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "File does not exist");;
		}
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
