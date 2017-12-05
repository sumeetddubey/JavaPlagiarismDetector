package comparator.hashCode.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import TestSamples.SampleFilePaths;
import comparator.hashcode.HashCodeComparator;
import interfaces.IComparator;
import utility.Report;
import utility.Report.ComparisonLayer;

public class HashCodeTests {

	@Test
	public void TestWhenTwoFilesAreExactSame() throws IOException {
		File f1 = new File(SampleFilePaths.fileFunctionSignatureSample1);
		File f2 = new File(SampleFilePaths.fileFunctionSignatureSample1);
		IComparator layer0 = new HashCodeComparator();
		Report r= layer0.generateReport(f1, f2);
		assertEquals(r.getLayer(), ComparisonLayer.HASHCODE);
		assertEquals(r.getScore(), 100, 0.1);
		assertEquals(r.getMessage(), "The files uploaded have an exact match");
	}
	
	@Test
	public void TestWhenTwoFilesAreNotExactlySame() throws IOException {
		File f1 = new File(SampleFilePaths.fileFunctionSignatureSample1);
		File f2 = new File(SampleFilePaths.fileFunctionSignatureSample2);
		IComparator layer0 = new HashCodeComparator();
		Report r= layer0.generateReport(f1, f2);
		assertEquals(r.getLayer(), ComparisonLayer.HASHCODE);
		assertEquals(r.getScore(), 0, 0.1);
		assertEquals(r.getMessage(), "The files uploaded do not have an exact match");
	}

}
