package comparator.functionSignature.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.Test;

import comparator.functionSignature.FunctionMatchPair;
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
	
//	test for comparing two programs
	public void testCompareTwoPrograms1() {
		String p1="comparator.functionSignature.tests.Sample1";
		String p2="comparator.functionSignature.tests.Sample2";
		float score=Layer1Detection.comparePrograms(p1, p2);
		HashSet<FunctionMatchPair> set = Layer1Detection.getMatchPairs();
		Iterator<FunctionMatchPair> it=set.iterator();
		while(it.hasNext()) {
			it.next().textualRepresentation();
		}
		assertEquals(score, 100, 0.01);
	}
	
//	test for comparing two programs
	public void testCompareTwoPrograms2() {
		String p1="comparator.functionSignature.tests.Sample1";
		String p2="comparator.functionSignature.tests.Sample3";
		float score=Layer1Detection.comparePrograms(p1, p2);
		assertEquals(score, 66.67, 0.01);
	}
	
//	test for comparing two programs
	public void testCompareTwoPrograms3() {
		String p1="comparator.functionSignature.tests.RBTree1.RBTree";
		String p2="comparator.functionSignature.tests.RBTree2.RedBlack";
		float score=Layer1Detection.comparePrograms(p1, p2);
		assertEquals(score, 8.33, 0.01);
	}
}
