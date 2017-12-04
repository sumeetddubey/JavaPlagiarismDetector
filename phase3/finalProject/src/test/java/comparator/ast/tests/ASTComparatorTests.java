package comparator.ast.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.function.IntPredicate;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import comparator.ast.ASTComparator;
import interfaces.IComparator;
import utility.Report;


/**
 * Test suite for testing ASTComparator
 * 
 * @author Wenjun
 *
 */
public class ASTComparatorTests {

	IComparator astComparator;
	static File fileTestSample1;
	static File fileTestSample2;
	static File fileTestSample3;
	static File fileTestSample4;
	static File fileTestSample5;
	
	static File fileEmptyFile;
	static File fileEmptyClass;
	
	static File fileLinkedListA;
	static File fileLinkedListB;
	
	static File fileWithNestedIf;
	static File fileWithSwitch;
	
	/**
	 * Initialize files with absolute file paths
	 */
	@BeforeClass
	public static void beforeClass() {
		String curDir = System.getProperty("user.dir");
		String prefix = curDir + File.separator + "src" + File.separator + "test" + File.separator + "java"
				+ File.separator + "comparator" + File.separator + "ast" + File.separator + "tests" + File.separator;
		
		fileTestSample1 = new File(prefix + "TestSample1.java");
		fileTestSample2 = new File(prefix + "TestSample2.java");
		fileTestSample3 = new File(prefix + "TestSample3.java");
		fileTestSample4 = new File(prefix + "TestSample4.java");
		fileTestSample5 = new File(prefix + "TestSample5.java");
		
		fileEmptyFile = new File(prefix + "EmptyJavaFile.java");
		fileEmptyClass = new File(prefix + "EmptyClass.java");
		
		fileLinkedListA = new File(prefix + "LinkedListA.java");
		fileLinkedListB = new File(prefix + "LinkedListB.java");
		fileWithNestedIf = new File(prefix + "ProgramWithNestedIfs.java");
		fileWithSwitch = new File(prefix + "ProgramWithSwitch.java");
	}
	

	/**
	 * Create ASTComparator for comparing two java source files
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		astComparator = new ASTComparator();
	}

	/**
	 * Helper method to parse String representation of suspicious line numbers
	 * to be an array of integers
	 * @param str
	 * @return
	 */
	public int[] parseStringToIntArr(String str) {
		str = str.substring(1, str.length() - 1);
		if (str.length() == 0) {
			return new int[] {};
		} else {
			String[] numsInStr = str.split(", ");
			int[] nums = new int[numsInStr.length];
			for(int i = 0; i < numsInStr.length; i++) {
				nums[i] = Integer.parseInt(numsInStr[i]);
			}
			return nums;
		}
	}
	
	@Test
	public void testTwoIdenticalFile() throws IOException {
		Report actual = astComparator.generateReport(fileTestSample1, fileTestSample1);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 100;
		int[] expectedLineNumsInA = new int[] {10, 11, 12, 13, 16, 17, 19, 20, 21, 22, 23, 25, 28, 29, 30, 32};
		int[] expectedLineNumsInB = new int[] {10, 11, 12, 13, 16, 17, 19, 20, 21, 22, 23, 25, 28, 29, 30, 32};
		
		assertEquals("2", actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	public void testEmptyFile() throws IOException {
		Report actual = astComparator.generateReport(fileTestSample1, fileEmptyFile);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 0;
		int[] expectedLineNumsInA = new int[] {};
		int[] expectedLineNumsInB = new int[] {};
		
		assertEquals("2", actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	public void testEmptyClass() throws IOException {
		Report actual = astComparator.generateReport(fileTestSample1, fileEmptyClass);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 0;
		int[] expectedLineNumsInA = new int[] {};
		int[] expectedLineNumsInB = new int[] {};
		
		assertEquals("2", actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	//public void testReplaceForLoopByWhileLoop() throws IOException {
	public void test1() throws IOException {
		Report actual = astComparator.generateReport(fileTestSample1, fileTestSample2);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 88.46;
		int[] expectedLineNumsInA = new int[] {10, 11, 12, 13, 16, 17, 19, 20, 21, 22, 25, 28, 29, 30, 32};
		int[] expectedLineNumsInB = new int[] {10, 11, 12, 13, 16, 17, 20, 21, 22, 23, 27, 30, 31, 32, 34};
		
		assertEquals("2", actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	//public void testExtractBlockToBeANewFunction() throws IOException {
	public void test2() throws IOException {
		Report actual = astComparator.generateReport(fileTestSample1, fileTestSample3);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 86.21;
		int[] expectedLineNumsInA = new int[] {10, 11, 12, 13, 16, 17, 19, 20, 21, 22, 23, 25, 28, 29, 30, 32};
		int[] expectedLineNumsInB = new int[] {10, 11, 17, 18, 21, 22, 24, 25, 26, 32, 33, 34, 37, 38, 39, 41};
		
		assertEquals("2", actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	//public void testReplaceSwithWithNestedIfs() throws IOException {
	public void test4() throws IOException {
		Report actual = astComparator.generateReport(fileWithSwitch, fileWithNestedIf);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 80.73;
		int[] expectedLineNumsInA = new int[] {8, 10, 12, 13, 16, 17, 18, 21, 22, 26, 27, 30, 31, 34, 35, 36, 37, 
				38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 62};
		int[] expectedLineNumsInB = new int[] {6, 8, 10, 11, 14, 15, 16, 19, 20, 24, 25, 28, 29, 32, 33, 34, 35, 
				36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 54};
		
		assertEquals("2", actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	//public void testTwoUnrelatedFiles1() throws IOException {
	public void test3() throws IOException {
		Report actual = astComparator.generateReport(fileTestSample1, fileTestSample4);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 36.92;
		int[] expectedLineNumsInA = new int[] {12, 19, 20, 28, 29, 30, 32};
		int[] expectedLineNumsInB = new int[] {8, 10, 12, 33, 35, 36, 41, 43};
		
		assertEquals("2", actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	public void testTwoUnrelatedFiles2() throws IOException {
		Report actual = astComparator.generateReport(fileTestSample1, fileTestSample5);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 45.16;
		int[] expectedLineNumsInA = new int[] {19, 20, 21, 22, 23, 25, 28, 29, 30};
		int[] expectedLineNumsInB = new int[] {7, 8, 10, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 27};
		
		assertEquals("2", actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	
	@Test
	//public void testReplaceGenricsByObject() throws IOException {
	public void test6() throws IOException {
		Report actual = astComparator.generateReport(fileLinkedListA, fileLinkedListB);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 100;
		int[] expectedLineNumsInA = new int[] {8, 9, 10, 13, 14, 16, 17, 18, 22, 23, 24, 27, 28, 29, 30, 31, 
				32, 33, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 48, 49, 52, 53, 56, 57, 58, 59, 61, 62, 63, 64, 
				65, 66, 67, 70, 71, 72, 73, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 91, 92, 93, 
				94, 95, 98, 99, 100, 101, 102, 105, 106, 107, 108, 109, 110, 111, 112, 115, 117, 122, 124, 125, 
				128, 130, 133, 135, 136, 137, 138, 139, 140};
		int[] expectedLineNumsInB = new int[] {9, 10, 12, 13, 14, 18, 19, 20, 23, 24, 25, 26, 27, 28, 29, 32, 
		                                        33, 34, 35, 36, 37, 38, 39, 40, 41, 44, 45, 48, 49, 52, 53, 54, 
		                                        55, 57, 58, 59, 60, 61, 62, 63, 66, 67, 68, 69, 71, 72, 73, 74, 
		                                        75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 87, 88, 89, 90, 91, 94, 
		                                        95, 96, 97, 98, 101, 102, 103, 104, 105, 106, 107, 108, 111, 113, 
		                                        118, 120, 121, 124, 126, 129, 131, 132, 133, 134, 135, 136, 141, 
		                                        142, 143};
		
		assertEquals("2", actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
}
