package comparator.functionSignature.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.Test;
import comparator.functionSignature.FunctionSignature;
import comparator.functionSignature.Layer1Detection;
import junit.framework.TestCase;

public class FunctionSignatureTests extends TestCase {
	@Test
//	test for generating function signature of a program
	public void testFunctionSignatureForProgram() {
		String name="foo";
		String returnType="void";
		ArrayList<String> args=new ArrayList<String>(Arrays.asList("int", "String", "int"));
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
	public void testCompareTwoPrograms1() throws ClassNotFoundException {
		String p1="comparator.functionSignature.tests.Sample1";
		String p2="comparator.functionSignature.tests.Sample2";
		Layer1Detection detect = new Layer1Detection();
		float score=detect.comparePrograms(p1, p2);
		assertEquals(score, 100, 0.01);
	}
	
//	test for comparing two programs where one program has 1 function that has no matching signature in the
//	other
//	total number of functions in smaller program is 3. Score :- (2/3 * 100) %
	public void testCompareTwoPrograms2() {
		String p1="comparator.functionSignature.tests.Sample1";
		String p2="comparator.functionSignature.tests.Sample3";
		Layer1Detection detect = new Layer1Detection();
		float score=detect.comparePrograms(p1, p2);
		assertEquals(score, 66.67, 0.01);
	}
	
//	test for comparing two complex programs.
	public void testCompareComplexPrograms() {
		String p1="comparator.functionSignature.tests.RBTree1.RBTree";
		String p2="comparator.functionSignature.tests.RBTree2.RedBlack";
		Layer1Detection detect = new Layer1Detection();
		float score=detect.comparePrograms(p1, p2);
		assertEquals(score, 8.33, 0.01);
		String res=detect.generateReport(p1, p2);
	}
}
