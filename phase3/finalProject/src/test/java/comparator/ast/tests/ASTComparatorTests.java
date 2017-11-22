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

public class ASTComparatorTests {

	IComparator astComparator;
	static File fileA;
	static File fileASimilar;
	
	@BeforeClass
	public static void beforeClass() {
		String curDir = System.getProperty("user.dir");
		
		fileA = new File(curDir + File.separator + "src" + File.separator + "test" 
						+ File.separator + "java" + File.separator + "comparator" + File.separator 
						+ "ast" + File.separator + "tests" + File.separator + "A.java");
		
		fileASimilar = new File(curDir + File.separator + "src" + File.separator + "test" 
				+ File.separator + "java" + File.separator + "comparator" + File.separator 
				+ "ast" + File.separator + "tests" + File.separator + "ASimilar.java");
	}
	
	@Before
	public void setUp() throws Exception {
		astComparator = new ASTComparator();
	}
	
	@Test
	public void test1() throws IOException {
		Report actual = astComparator.generateReport(fileA, fileASimilar);

		Report expected = new Report("2", (float)85.71, "[4, 5, 6, 7, 8, 9, 10, 11, 13, 14, 17, 18, 19, 20, 21, 22, 23, 24]\n" + 
				"[4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 16, 20, 21, 22, 23, 24, 25, 26, 27]");
		
		assertEquals(expected.getLayer(), actual.getLayer());
		assertEquals(expected.getScore(), actual.getScore(), 0.01);
		assertEquals(expected.getMessage(), actual.getMessage());
	}
}
