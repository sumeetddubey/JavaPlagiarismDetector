package comparator.functionSignature.tests;

import java.io.File;
import java.util.HashMap;
import org.junit.Test;
import TestSamples.SampleFilePaths;
import comparator.functionSignature.FunctionSignature;
import comparator.functionSignature.FunctionSignatureComparator;
import junit.framework.TestCase;
import utility.ReadFileToString;
import utility.Report;
import utility.Report.ComparisonLayer;

// Class for test function signature comparison
public class FunctionSignatureTests extends TestCase {
	@Test
//	test for generating function signature of a program
	public void testFunctionSignatureForProgram() {
		String name="foo";
		String returnType="void";
		String[] args=new String[] {"int", "int", "String"};
		FunctionSignature fs=new FunctionSignature(name, args, returnType);
		HashMap<String, Integer> argsMap=new HashMap<String, Integer>();
		argsMap.put("int", 2);
		argsMap.put("String", 1);
		assertEquals(fs.getName(), "foo");
		assertEquals(fs.getReturnType(), "void");
		assertEquals(fs.getArgs(), argsMap);
	}
	
	@Test
//	test for generating function signature of a program with spaces
	public void testFunctionSignatureWithSpace() {
		String name="foo";
		String returnType="void";
		String[] args=new String[] {" int	", " int ", " String "};
		FunctionSignature fs=new FunctionSignature(name, args, returnType);
		HashMap<String, Integer> argsMap=new HashMap<String, Integer>();
		argsMap.put("int", 2);
		argsMap.put("String", 1);
		assertEquals(fs.getName(), "foo");
		assertEquals(fs.getReturnType(), "void");
		assertEquals(fs.getArgs(), argsMap);
	}
	
//	test for comparing two programs where one program has all signatures matching in the second 
//	total number of functions in smaller program is 3. Score :- (3/3 * 100) %
	@Test
	public void testCompareTwoPrograms1(){
		String program1=ReadFileToString.readFileToString(SampleFilePaths.fileFunctionSignatureSample1);
		String program2=ReadFileToString.readFileToString(SampleFilePaths.fileFunctionSignatureSample2);
		FunctionSignatureComparator detect = new FunctionSignatureComparator();
		float score=detect.comparePrograms(program1, program2);
		assertEquals(score, 100, 0.01);
	}
	
//	test for comparing two programs where one program has 1 function that has no matching signature in the
//	other
//	total number of functions in smaller program is 4. Score :- (3/4 * 100) %
	@Test
	public void testCompareTwoPrograms2(){
		String program1=ReadFileToString.readFileToString(SampleFilePaths.fileFunctionSignatureSample1);
		String program2=ReadFileToString.readFileToString(SampleFilePaths.fileFunctionSignatureSample3);
		FunctionSignatureComparator detect = new FunctionSignatureComparator();
		float score=detect.comparePrograms(program1, program2);
		assertEquals(score, 75.0, 0.01);
	}
	
//	Test when 1st program has lesser number of functions than 2nd program
	@Test
	public void testCompareTwoProgramWhenProgram1HasLesserNumberOfFunctions(){
		String program1=ReadFileToString.readFileToString(SampleFilePaths.fileFunctionSignatureSample4);
		String program2=ReadFileToString.readFileToString(SampleFilePaths.fileFunctionSignatureSample3);
		FunctionSignatureComparator detect = new FunctionSignatureComparator();
		float score=detect.comparePrograms(program1, program2);
		assertEquals(score, 33.33, 0.01);
	}
	
//	Test when program 1 has no functions 
	@Test
	public void testCompareWhenProgram1HasNoFunctions(){
		String program1=ReadFileToString.readFileToString(SampleFilePaths.fileFunctionSignatureSample5);
		String program2=ReadFileToString.readFileToString(SampleFilePaths.fileFunctionSignatureSample4);
		FunctionSignatureComparator detect = new FunctionSignatureComparator();
		float score=detect.comparePrograms(program1, program2);
		assertEquals(score, 0, 0.01);
	}
	
//	Test when program 2 has no functions 
	@Test
	public void testCompareWhenProgram2HasNoFunctions(){
		String program1=ReadFileToString.readFileToString(SampleFilePaths.fileFunctionSignatureSample4);
		String program2=ReadFileToString.readFileToString(SampleFilePaths.fileFunctionSignatureSample5);
		FunctionSignatureComparator detect = new FunctionSignatureComparator();
		float score=detect.comparePrograms(program1, program2);
		assertEquals(score, 0, 0.01);
	}
	
//	Test when one program has a generic return type
	@Test
	public void testCompareProgramWithGerericReturnTypeFunction(){
		String program1=ReadFileToString.readFileToString(SampleFilePaths.fileFunctionSignatureSample5);
		String program2=ReadFileToString.readFileToString(SampleFilePaths.fileFunctionSignatureSample6);
		FunctionSignatureComparator detect = new FunctionSignatureComparator();
		float score=detect.comparePrograms(program1, program2);
		assertEquals(score, 0, 0.01);
	}
	
//	test for comparing reports of two programs when there are no matching signatures
	@Test
	public void testGeneratedReportForNoSignatureMatch(){
		String program1=ReadFileToString.readFileToString(SampleFilePaths.fileFunctionSignatureSample5);
		String program2=ReadFileToString.readFileToString(SampleFilePaths.fileFunctionSignatureSample6);
		FunctionSignatureComparator detect = new FunctionSignatureComparator();
		Report r = detect.generateReport(program1,  program2);
		String expectedMessage="No functions with matching signatures";
		assertEquals(r.getScore(), 0, 0.01);
		assertEquals(r.getLayer(), ComparisonLayer.FUNCTION_SIGNATURE);
		assertEquals(r.getMessage(), expectedMessage);
	}
	
//	test for comparing reports of two programs when there is a function signature match
	@Test
	public void testReportGenerationForSignatureComparisonWithMatch(){
		String program1=ReadFileToString.readFileToString(SampleFilePaths.fileFunctionSignatureSample6);
		String program2=ReadFileToString.readFileToString(SampleFilePaths.fileFunctionSignatureSample7);
		FunctionSignatureComparator detect = new FunctionSignatureComparator();
		Report r = detect.generateReport(program1,  program2);
		String expectedMessage="foo from program 1 matches with bar from program 2\n";
		assertEquals(r.getScore(), 100, 0.01);
		assertEquals(r.getLayer(), ComparisonLayer.FUNCTION_SIGNATURE);
		assertEquals(r.getMessage(), expectedMessage);
	}
	
//	test for comparing two Linked List Programs.
	@Test
	public void testCompareComplexPrograms1(){
		String program1=ReadFileToString.readFileToString(SampleFilePaths.fileLinkedListA);
		String program2=ReadFileToString.readFileToString(SampleFilePaths.fileLinkedListB);
		FunctionSignatureComparator detect = new FunctionSignatureComparator();
		float score=detect.comparePrograms(program1, program2);
		assertEquals(score, 33.33, 0.01);
	}
	
//	test for comparing two complex linked list programs
	@Test
	public void testCompareComplexPrograms2(){
		String program1=ReadFileToString.readFileToString(SampleFilePaths.fileSet02Sample1LinkedList);
		String program2=ReadFileToString.readFileToString(SampleFilePaths.fileSet02Sample2LinkedList);
		FunctionSignatureComparator detect = new FunctionSignatureComparator();
		float score=detect.comparePrograms(program1, program2);
		assertEquals(score, 36.36, 0.01);
	}
	
//	test for comparing two programs when one is empty
	public void testCompareEmptyProgram() {
		String program1=ReadFileToString.readFileToString(SampleFilePaths.fileFunctionSignatureSample1);
		String program2=ReadFileToString.readFileToString(SampleFilePaths.fileEmptyFile);
		FunctionSignatureComparator detect = new FunctionSignatureComparator();
		float score=detect.comparePrograms(program1, program2);
		assertEquals(score, 0, 0.01);
	}
	
//	test for comparing two programs when one is null
	public void testCompareNull() {
		String program1=ReadFileToString.readFileToString(SampleFilePaths.fileFunctionSignatureSample1);
		String program2=null;
		FunctionSignatureComparator detect = new FunctionSignatureComparator();
		float score=detect.comparePrograms(program1, program2);
		assertEquals(score, 0, 0.01);
	}
}
