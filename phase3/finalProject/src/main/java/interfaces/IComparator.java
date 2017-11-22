package interfaces;

import java.io.File;
import java.io.IOException;

import utility.Report;

public interface IComparator {
	
	/**
	 * Returns generated report of given programs to PlagiarismDetector
	 * @throws IOException 
	 */
	
	Report generateReport(File programA, File programB) throws IOException;
}
