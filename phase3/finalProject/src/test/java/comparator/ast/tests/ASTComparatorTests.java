package comparator.ast.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

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
	static File fileA;
	static File fileASimilar;
	static File fileAExtended;
	static File fileB;
	
	/**
	 * Initialize files with absolute file paths
	 */
	@BeforeClass
	public static void beforeClass() {
		String curDir = System.getProperty("user.dir");
		
		fileA = new File(curDir + File.separator + "src" + File.separator + "test" 
						+ File.separator + "java" + File.separator + "comparator" + File.separator 
						+ "ast" + File.separator + "tests" + File.separator + "A.java");
		
		fileASimilar = new File(curDir + File.separator + "src" + File.separator + "test" 
				+ File.separator + "java" + File.separator + "comparator" + File.separator 
				+ "ast" + File.separator + "tests" + File.separator + "ASimilar.java");
		
		fileAExtended = new File(curDir + File.separator + "src" + File.separator + "test" + File.separator + "java"
				+ File.separator + "comparator" + File.separator + "ast" + File.separator + "tests" + File.separator
				+ "AExtended.java");
		
		fileB = new File(curDir + File.separator + "src" + File.separator + "test" + File.separator + "java"
				+ File.separator + "comparator" + File.separator + "ast" + File.separator + "tests" + File.separator
				+ "B.java");
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

	
	@Test
	public void test1() throws IOException {
		Report actual = astComparator.generateReport(fileA, fileASimilar);

		Report expected = new Report("2", (float)89.47, 
				"[10, 11, 12, 13, 16, 17, 20, 23, 26, 27, 28, 30]" +"\n" + 
				"[11, 12, 14, 15, 18, 19, 23, 27, 30, 31, 32, 34]");
		
		assertEquals(expected.getLayer(), actual.getLayer());
		assertEquals(expected.getScore(), actual.getScore(), 0.01);
		assertEquals(expected.getMessage(), actual.getMessage());
	}
	
	@Test
	public void test2() throws IOException {
		Report actual = astComparator.generateReport(fileA, fileAExtended);

		Report expected = new Report("2", (float)86.36, 
				"[10, 11, 12, 13, 16, 17, 19, 20, 21, 23, 26, 27, 28, 30]" + "\n" + 
				"[10, 11, 17, 18, 21, 22, 24, 25, 26, 32, 35, 36, 37, 39]");
		
		assertEquals(expected.getLayer(), actual.getLayer());
		assertEquals(expected.getScore(), actual.getScore(), 0.01);
		assertEquals(expected.getMessage(), actual.getMessage());
	}
	
	@Test
	public void test3() throws IOException {
		Report actual = astComparator.generateReport(fileA, fileB);

		Report expected = new Report("2", (float)22.22, 
				"[23, 26, 27, 28]" + "\n" + 
				"[29, 33, 35, 36]");
		
		assertEquals(expected.getLayer(), actual.getLayer());
		assertEquals(expected.getScore(), actual.getScore(), 0.01);
		assertEquals(expected.getMessage(), actual.getMessage());
	}
	
}
