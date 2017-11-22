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
	public static void setUpBeforeClass() throws Exception {
		String curDir = System.getProperty("user.dir");
		fileA = new File(curDir + File.separator + "src" + File.separator + "test" 
						+ File.separator + "java" + File.separator + "comparator" + File.separator 
						+ "ast" + File.separator + "tests" + File.separator + "A.java");
		fileA = new File(curDir + File.separator + "src" + File.separator + "test" 
				+ File.separator + "java" + File.separator + "comparator" + File.separator 
				+ "ast" + File.separator + "tests" + File.separator + "ASimilar.java");
	}
	
	@Before
	public void setUp() throws Exception {
		astComparator = new ASTComparator();
	}


//	@Test
//	public void test() throws IOException {
//		Report actual = astComparator.generateReport(fileA, fileASimilar);
//		Report expected = new Report("2", (float)100.0, "");
//		assertEquals(actual, expected);
//	}

}
