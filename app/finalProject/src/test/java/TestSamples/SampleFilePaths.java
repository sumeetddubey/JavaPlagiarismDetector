package TestSamples;

import java.io.File;

/**
 * List all absolute file paths for sample programs under TestSamples folder
 * 
 * @author Wenjun
 *
 */
public class SampleFilePaths {
	public static final String curDir = System.getProperty("user.dir");
	public static final String filePathPrefix = curDir + File.separator + "src" 
							+ File.separator + "test" + File.separator + "java"
							+ File.separator + "TestSamples" + File.separator;

	public static final String fileWithForLoop = filePathPrefix + "ProgramWithForLoop.java";
	public static final String fileWithWhileLoop = filePathPrefix + "ProgramWithWhileLoop.java";
	public static final String fileWithForLoopModified = filePathPrefix + "ProgramWithForLoopModified.java";
	public static final String fileBinarySearch = filePathPrefix + "BinarySearch.java";
	public static final String fileLCS = filePathPrefix + "LongestCommonSubsequence.java";
	public static final String fileLetterCombinationsOfAPhoneNumber = 
			filePathPrefix + "ProgramLetterCombinationsOfAPhoneNumber.java";
	
	public static final String fileEmptyFile = filePathPrefix + "EmptyJavaFile.java";
	public static final String fileEmptyClass = filePathPrefix + "EmptyClass.java";
	
	public static final String fileLinkedListA  = filePathPrefix + "LinkedListA.java";
	public static final String fileLinkedListB = filePathPrefix + "LinkedListB.java";
	
	public static final String fileWithNestedIf = filePathPrefix + "ProgramWithNestedIfs.java";
	public static final String fileWithSwitch = filePathPrefix + "ProgramWithSwitch.java";
	
	public static final String fileASTStringSample1 = filePathPrefix + "ASTStringSample1.java";
	public static final String fileASTStringSample2 = filePathPrefix + "ASTStringSample2.java";

	public static final String fileASTStringSample3 = filePathPrefix + "ASTStringSample3.java";
	public static final String fileASTStringSample4 = filePathPrefix + "ASTStringSample4.java";
	
	
	public static final String fileFunctionSignatureSample1 = filePathPrefix + "FunctionSignatureSample1.java";
	public static final String fileFunctionSignatureSample2 = filePathPrefix + "FunctionSignatureSample2.java";
	public static final String fileFunctionSignatureSample3 = filePathPrefix + "FunctionSignatureSample3.java";
	public static final String fileFunctionSignatureSample4 = filePathPrefix + "FunctionSignatureSample4.java";
	public static final String fileFunctionSignatureSample5 = filePathPrefix + "FunctionSignatureSample5.java";
	public static final String fileFunctionSignatureSample6 = filePathPrefix + "FunctionSignatureSample6.java";
	public static final String fileFunctionSignatureSample7 = filePathPrefix + "FunctionSignatureSample7.java";
	
	public static final String fileSet02Sample1LinkedList = filePathPrefix + "set02" + File.separator + "Sample1"
			+ File.separator + "LinkedList.java";
	public static final String fileSet02Sample2LinkedList = filePathPrefix + "set02" + File.separator + "Sample2"
			+ File.separator + "LinkedList.java";
}
