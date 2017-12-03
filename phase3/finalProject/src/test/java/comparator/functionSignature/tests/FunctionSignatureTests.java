package comparator.functionSignature.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.Test;
import comparator.functionSignature.FunctionSignature;
import comparator.functionSignature.Layer1Detection;
import junit.framework.TestCase;
import utility.Report;

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
//	test for generating function signature of a program
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
		File f1=new File("src/test/java/comparator/functionSignature/tests/Sample1.java");
		File f2=new File("src/test/java/comparator/functionSignature/tests/Sample2.java");
		Layer1Detection detect = new Layer1Detection();
		float score=detect.comparePrograms(f1, f2);
		assertEquals(score, 100, 0.01);
	}
	
//	test that generates a report after comparing two programs
	public void testCompareTwoProgramsThroughReport(){
		File f1=new File("src/test/java/comparator/functionSignature/tests/Sample1.java");
		File f2=new File("src/test/java/comparator/functionSignature/tests/Sample2.java");
		Layer1Detection detect = new Layer1Detection();
		Report r = detect.generateReport(f1, f2);
		assertEquals(r.getScore(), 100, 0.01);
	}
	
//	test when one of the files does not exist
	public void testFileDoesNotExist(){
		File f1=new File("src/test/java/comparator/functionSignature/tests/SampleDoesNotExist.java");
		File f2=new File("src/test/java/comparator/functionSignature/tests/Sample2.java");
		Layer1Detection detect = new Layer1Detection();
		Report r = detect.generateReport(f1, f2);
//		assertEquals(r.getScore(), 100, 0.01);
	}
	
//	test for comparing two programs where one program has 1 function that has no matching signature in the
//	other
//	total number of functions in smaller program is 3. Score :- (2/3 * 100) %
	@Test
	public void testCompareTwoPrograms2() {
		File f1=new File("src/test/java/comparator/functionSignature/tests/Sample1.java");
		File f2=new File("src/test/java/comparator/functionSignature/tests/Sample3.java");
		Layer1Detection detect = new Layer1Detection();
		float score=detect.comparePrograms(f1, f2);
		assertEquals(score, 75.0, 0.01);
	}
	
//	Test when 1st program has lesser number of functions than 2nd program
	@Test
	public void testCompareTwoProgramWhenProgram1HasLesserNumberOfFunctions(){
		File f1=new File("src/test/java/comparator/functionSignature/tests/Sample4.java");
		File f2=new File("src/test/java/comparator/functionSignature/tests/Sample3.java");
		Layer1Detection detect = new Layer1Detection();
		float score=detect.comparePrograms(f1, f2);
		assertEquals(score, 33.33, 0.01);
	}
	
//	Test when program 1 has no functions 
	@Test
	public void testCompareWhenProgram1HasNoFunctions(){
		File f1=new File("src/test/java/comparator/functionSignature/tests/Sample5.java");
		File f2=new File("src/test/java/comparator/functionSignature/tests/Sample4.java");
		Layer1Detection detect = new Layer1Detection();
		float score=detect.comparePrograms(f1, f2);
		assertEquals(score, 0, 0.01);
	}
	
//	Test when program 2 has no functions 
	@Test
	public void testCompareWhenProgram2HasNoFunctions(){
		File f1=new File("src/test/java/comparator/functionSignature/tests/Sample4.java");
		File f2=new File("src/test/java/comparator/functionSignature/tests/Sample5.java");
		Layer1Detection detect = new Layer1Detection();
		float score=detect.comparePrograms(f1, f2);
		assertEquals(score, 0, 0.01);
	}
	
//	Test when program 2 has no functions 
	@Test
	public void testCompareProgramWithGerericReturnTypeFunction(){
		File f1=new File("src/test/java/comparator/functionSignature/tests/Sample5.java");
		File f2=new File("src/test/java/comparator/functionSignature/tests/Sample6.java");
		Layer1Detection detect = new Layer1Detection();
		float score=detect.comparePrograms(f1, f2);
		assertEquals(score, 0, 0.01);
	}
	
//	test for comparing two complex programs.
	@Test
	public void testCompareComplexPrograms1() {
		File f1=new File("src/test/java/comparator/functionSignature/tests/set01/Sample1/LinkedList.java");
		File f2=new File("src/test/java/comparator/functionSignature/tests/set01/Sample2/SimpleLinkedList.java");
		Layer1Detection detect = new Layer1Detection();
		float score=detect.comparePrograms(f1, f2);
		assertEquals(score, 73.33, 0.01);
	}
	
//	test for comparing two complex programs.
	@Test
	public void testCompareComplexPrograms2() {
		File f1=new File("src/test/java/comparator/functionSignature/tests/set02/Sample1/LinkedList.java");
		File f2=new File("src/test/java/comparator/functionSignature/tests/set02/Sample2/LinkedList.java");
		Layer1Detection detect = new Layer1Detection();
		float score=detect.comparePrograms(f1, f2);
		assertEquals(score, 81.81, 0.01);
	}
}
