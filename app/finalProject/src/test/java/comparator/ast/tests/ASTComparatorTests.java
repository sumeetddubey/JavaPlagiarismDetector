package comparator.ast.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import TestSamples.SampleFilePaths;
import comparator.ast.ASTComparator;
import interfaces.IComparator;
import utility.ReadFileToString;
import utility.Report;
import utility.Report.ComparisonLayer;



/**
 * Test suite for testing ASTComparator
 * 
 * @author Wenjun
 *
 */
public class ASTComparatorTests {

	IComparator astComparator;

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
	public void testTwoIdenticalFile() {
		String fileWithForLoop = ReadFileToString.readFileToString(SampleFilePaths.fileWithForLoop);
		Report actual = astComparator.generateReport(fileWithForLoop, fileWithForLoop);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 100;
		int[] expectedLineNumsInA = new int[] {10, 11, 12, 13, 16, 17, 19, 20, 21, 22, 23, 25, 28, 29, 30, 32};
		int[] expectedLineNumsInB = new int[] {10, 11, 12, 13, 16, 17, 19, 20, 21, 22, 23, 25, 28, 29, 30, 32};
		
		assertEquals(ComparisonLayer.AST, actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	public void testEmptyFile1() {
		String fileWithForLoop = ReadFileToString.readFileToString(SampleFilePaths.fileWithForLoop);
		String fileEmptyFile = ReadFileToString.readFileToString(SampleFilePaths.fileEmptyFile);
		Report actual = astComparator.generateReport(fileWithForLoop, fileEmptyFile);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 0;
		int[] expectedLineNumsInA = new int[] {};
		int[] expectedLineNumsInB = new int[] {};
		
		assertEquals(ComparisonLayer.AST, actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	public void testEmptyFile2() {
		String fileWithForLoop = ReadFileToString.readFileToString(SampleFilePaths.fileWithForLoop);
		String fileEmptyFile = ReadFileToString.readFileToString(SampleFilePaths.fileEmptyFile);
		Report actual = astComparator.generateReport(fileEmptyFile, fileWithForLoop);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 0;
		int[] expectedLineNumsInA = new int[] {};
		int[] expectedLineNumsInB = new int[] {};
		
		assertEquals(ComparisonLayer.AST, actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	public void testEmptyClass1() {
		String fileWithForLoop = ReadFileToString.readFileToString(SampleFilePaths.fileWithForLoop);
		String fileEmptyClass = ReadFileToString.readFileToString(SampleFilePaths.fileEmptyClass);
		Report actual = astComparator.generateReport(fileWithForLoop, fileEmptyClass);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 0;
		int[] expectedLineNumsInA = new int[] {};
		int[] expectedLineNumsInB = new int[] {};
		
		assertEquals(ComparisonLayer.AST, actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	public void testEmptyClass2() {
		String fileWithForLoop = ReadFileToString.readFileToString(SampleFilePaths.fileWithForLoop);
		String fileEmptyClass = ReadFileToString.readFileToString(SampleFilePaths.fileEmptyClass);
		Report actual = astComparator.generateReport(fileEmptyClass, fileWithForLoop);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 0;
		int[] expectedLineNumsInA = new int[] {};
		int[] expectedLineNumsInB = new int[] {};
		
		assertEquals(ComparisonLayer.AST, actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	public void testReplaceForLoopByWhileLoop() {
		String fileWithForLoop = ReadFileToString.readFileToString(SampleFilePaths.fileWithForLoop);
		String fileWithWhileLoop = ReadFileToString.readFileToString(SampleFilePaths.fileWithWhileLoop);
		Report actual = astComparator.generateReport(fileWithForLoop, fileWithWhileLoop);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 88.46;
		int[] expectedLineNumsInA = new int[] {10, 11, 12, 13, 16, 17, 19, 20, 21, 22, 25, 28, 29, 30, 32};
		int[] expectedLineNumsInB = new int[] {10, 11, 12, 13, 16, 17, 20, 21, 22, 23, 27, 30, 31, 32, 34};
		
		assertEquals(ComparisonLayer.AST, actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	public void testExtractBlockToBeANewFunction() {
		String fileWithForLoop = ReadFileToString.readFileToString(SampleFilePaths.fileWithForLoop);
		String fileWithForLoopModified = ReadFileToString.readFileToString(SampleFilePaths.fileWithForLoopModified);
		Report actual = astComparator.generateReport(fileWithForLoop, fileWithForLoopModified);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 86.21;
		int[] expectedLineNumsInA = new int[] {10, 11, 12, 13, 16, 17, 19, 20, 21, 22, 23, 25, 28, 29, 30, 32};
		int[] expectedLineNumsInB = new int[] {10, 11, 17, 18, 21, 22, 24, 25, 26, 32, 33, 34, 37, 38, 39, 41};
		
		assertEquals(ComparisonLayer.AST, actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	public void testReplaceSwithWithNestedIfs() {
		String fileWithSwitch = ReadFileToString.readFileToString(SampleFilePaths.fileWithSwitch);
		String fileWithNestedIf = ReadFileToString.readFileToString(SampleFilePaths.fileWithNestedIf);
		Report actual = astComparator.generateReport(fileWithSwitch, fileWithNestedIf);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 80.73;
		int[] expectedLineNumsInA = new int[] {6, 8, 10, 11, 14, 15, 16, 19, 20, 24, 25, 28, 29, 32, 33, 34, 35, 
		                                        36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 
		                                        52, 53, 54, 55, 56, 57, 58, 60};
		int[] expectedLineNumsInB = new int[] {6, 8, 10, 11, 14, 15, 16, 19, 20, 24, 25, 28, 29, 32, 33, 34, 
		                                        35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 
		                                        50, 51, 52, 54};
		
		assertEquals(ComparisonLayer.AST, actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	public void testTwoUnrelatedFiles1() {
		String fileWithForLoop = ReadFileToString.readFileToString(SampleFilePaths.fileWithForLoop);
		String fileBinarySearch = ReadFileToString.readFileToString(SampleFilePaths.fileBinarySearch);
		Report actual = astComparator.generateReport(fileWithForLoop, fileBinarySearch);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 36.92;
		int[] expectedLineNumsInA = new int[] {12, 19, 20, 28, 29, 30, 32};
		int[] expectedLineNumsInB = new int[] {8, 10, 12, 33, 35, 36, 41, 43};
		
		assertEquals(ComparisonLayer.AST, actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	public void testTwoUnrelatedFiles2() {

		String fileWithForLoop = ReadFileToString.readFileToString(SampleFilePaths.fileWithForLoop);
		String fileLetterCombinationsOfAPhoneNumber =
					ReadFileToString.readFileToString(SampleFilePaths.fileLetterCombinationsOfAPhoneNumber);
		Report actual = astComparator.generateReport(fileWithForLoop, fileLetterCombinationsOfAPhoneNumber);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 28.85;
		int[] expectedLineNumsInA = new int[] {19, 20, 21, 22, 23, 25, 28, 29, 30, 32};
		int[] expectedLineNumsInB = new int[] {7, 8, 9, 15, 16, 17, 18, 50, 51, 52, 53, 55, 58};
		
		assertEquals(ComparisonLayer.AST, actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	
	@Test
	public void testReplaceGenricsByObject() {
		String fileLinkedListA = ReadFileToString.readFileToString(SampleFilePaths.fileLinkedListA);
		String fileLinkedListB = ReadFileToString.readFileToString(SampleFilePaths.fileLinkedListB);
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
		
		assertEquals(ComparisonLayer.AST, actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	
	@Test
	public void testOddLenMatchWithOddSatartIndex() {
		String fileASTStringSample1 = ReadFileToString.readFileToString(SampleFilePaths.fileASTStringSample1);
		String fileASTStringSample2 = ReadFileToString.readFileToString(SampleFilePaths.fileASTStringSample2);
		Report actual = astComparator.generateReport(fileASTStringSample1, fileASTStringSample2);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 80.0;
		int[] expectedLineNumsInA = new int[] {5, 6, 10, 11, 12, 13, 14, 16};
		int[] expectedLineNumsInB = new int[] {4, 5, 7, 8, 9, 10, 11, 13};
		
		assertEquals(ComparisonLayer.AST, actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	public void testEvenLenMatchWithOddSatartIndex() {
		String fileASTStringSample3 = ReadFileToString.readFileToString(SampleFilePaths.fileASTStringSample3);
		String fileASTStringSample4 = ReadFileToString.readFileToString(SampleFilePaths.fileASTStringSample4);
		Report actual = astComparator.generateReport(fileASTStringSample3, fileASTStringSample4);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 66.67;
		int[] expectedLineNumsInA = new int[] {6, 7, 9, 10, 11, 12, 13, 15};
		int[] expectedLineNumsInB = new int[] {7, 8, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
		
		assertEquals(ComparisonLayer.AST, actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	public void testInputStringIsNull1() {
		String file = null;
		String fileASTStringSample4 = ReadFileToString.readFileToString(SampleFilePaths.fileASTStringSample4);
		Report actual = astComparator.generateReport(file, fileASTStringSample4);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 0;
		int[] expectedLineNumsInA = new int[] {};
		int[] expectedLineNumsInB = new int[] {};
		
		assertEquals(ComparisonLayer.AST, actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
	
	@Test
	public void testInputStringIsNull2() {
		String file = null;
		String fileASTStringSample4 = ReadFileToString.readFileToString(SampleFilePaths.fileASTStringSample4);
		Report actual = astComparator.generateReport(fileASTStringSample4, file);
		String message = actual.getMessage();
		String[] suspiciousLineNums = message.split("\n");
		int[] actualLineNumsInA = parseStringToIntArr(suspiciousLineNums[0]);
		int[] actualLineNumsInB = parseStringToIntArr(suspiciousLineNums[1]);
		
		float expectedScore = (float) 0;
		int[] expectedLineNumsInA = new int[] {};
		int[] expectedLineNumsInB = new int[] {};
		
		assertEquals(ComparisonLayer.AST, actual.getLayer());
		assertEquals(expectedScore, actual.getScore(), 0.01);
		assertArrayEquals(expectedLineNumsInA, actualLineNumsInA);
		assertArrayEquals(expectedLineNumsInB, actualLineNumsInB);
	}
}
