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
	 * Get the canonical name for this file. This will return the package name followed by file name.
	 * @param programA
	 * @return
	 */
//	private String getCanonicalName(File programA) {
//		String name = programA.getName();
//		name=name.substring(0, name.indexOf('.'));
//		name = "resource." + name;
//		return name;
//	}

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
	
	/**
	 * Function for getting all function signatures of a class
	 * @param className
	 * @return list of function signatures
	 */
	private ArrayList<FunctionSignature> getAllMethods(File classFile) {
//		ArrayList<FunctionSignature> fns=new ArrayList<FunctionSignature>();	
//		try {
//			Class<?> c=Class.forName(className);
//			Method[] methods=c.getDeclaredMethods();
////			iterating over all methods to get their arguments and storing
////			in an array list
//			for(Method m: methods) {
//				FunctionSignature fs = createFunctionSignature(m);
//				fns.add(fs);
//			}
//		}
////		will catch when a class definition is not found 
//		catch(Exception e) {
//			System.out.println("error is " +e);
//		}
//		return fns;
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
	 * Generates a function signature for given method
	 * @param m - a method object 
	 * @return function signature
	 */
//	private FunctionSignature createFunctionSignature(Method m) {
//		String name;
//		String returnType;
//		name=m.getName();
//		returnType=m.getReturnType().getSimpleName();
//		ArrayList<String> params=extractFunctionArguments(m.getParameterTypes());
//		FunctionSignature fs=new FunctionSignature(name, params, returnType);
//		return fs;
//	}

	/**
	 * Function for extracting the argument types for a method
	 * @param paramTypes - array of class objects giving the parameter types for a method
	 * @return parameter array
	 */
//	private ArrayList<String> extractFunctionArguments(Class<?>[] paramTypes) {
//		ArrayList<String> params = new ArrayList<String>();
//		if(paramTypes.length==0) {
//			params.add("none");
//		}
//		else {
//			for(Class<?> param: paramTypes) {
//				params.add(param.getSimpleName());
//			}
//		}
//		return params;
//	}

	/**
	 * Adds a pair of functions to matchPairs set that have matching signatures
	 * @param fs1 - function signature 1
	 * @param fs2 - function signature 2
	 */
	private void addToMatchPairs(FunctionSignature fs1, FunctionSignature fs2) {
		matchPairs.add(new FunctionMatchPair(fs1, fs2));
	}
	
	
//	/**
//	 * Get all match pairs
//	 */
//	public HashSet<FunctionMatchPair> getMatchPairs(){
//		return matchPairs;
//	}
	
	
	/**
	 * @param line
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
	 * @param line
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
	 * @param line
	 */
//	private String extractReturnType(String line) {
//		String returnType="";
//		for(String type: returnTypes.toArray(new String[returnTypes.size()])) {
//			if(line.contains(" " +type +" ")) {
//				returnType=type;	
//			}
//			else if(line.contains(" " +type +"[]")){
//				returnType=type+"[]";
//			}
//		}
//		return returnType;
//	}
	
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
