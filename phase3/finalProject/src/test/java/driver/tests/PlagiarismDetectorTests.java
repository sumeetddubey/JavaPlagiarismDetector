package driver.tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import TestSamples.SampleFilePaths;
import driver.PlagiarismDetector;
import utility.Report;
import utility.Report.ComparisonLayer;
public class PlagiarismDetectorTests {

	@Test
	public void testTwoFiles() {
		File file1 = new File(SampleFilePaths.fileFunctionSignatureSample6);
		File file2 = new File(SampleFilePaths.fileFunctionSignatureSample7);
		
		PlagiarismDetector detector = new PlagiarismDetector(file1, file2);
		Report[] reports = detector.generateFinalReport();
		
		// check if layer 0 matches what is expected
		assertEquals(ComparisonLayer.HASHCODE, reports[0].getLayer());
		assertEquals(0, reports[0].getScore(), 0.1);
		assertEquals("The files uploaded do not have an exact match", reports[0].getMessage());

		// check if layer 1 matches what is expected
		assertEquals(ComparisonLayer.FUNCTION_SIGNATURE, reports[1].getLayer());
		assertEquals(100, reports[1].getScore(), 0.1);
		String expectedMsgForLayer1 ="foo from program 1 matches with bar from program 2\n";
		assertEquals(expectedMsgForLayer1, reports[1].getMessage());

		// check if layer 2 matches what is expected
		assertEquals(ComparisonLayer.AST, reports[2].getLayer());
		assertEquals(57.14, reports[2].getScore(), 0.01);
		String expectedMsgForLayer2 ="[6, 7]" + "\n" + "[4, 5]";
		assertEquals(expectedMsgForLayer2, reports[2].getMessage());
	}
	
	@Test(expected = RuntimeException.class)
	public void testForNonExistingFile() {
		File file1 = new File(SampleFilePaths.fileFunctionSignatureSample1);
		File file2 = new File("not a file path");

		PlagiarismDetector detector = new PlagiarismDetector(file1, file2);
		detector.generateFinalReport();
	}
}
