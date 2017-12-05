package comparator.hashcode;

import java.io.File;
import java.io.IOException;

import interfaces.IComparator;
import utility.ReadFileToString;
import utility.Report;
import utility.Report.ComparisonLayer;

/**
 * HashCodeComparator checks if two programs are exactly same by using hash code
 */

public class HashCodeComparator implements IComparator {

	/**
	 * Returns generated report of given programs to PlagiarismDetector
	 * @throws IOException 
	 */
	public Report generateReport(File programA, File programB) throws IOException {

		hashCodeA = generateHashCode(programA);
		hashCodeB = generateHashCode(programB);
		
		boolean isSame = compareHashCode();
		
		return writeReport(isSame);
	}
	
	
	/**
	 * Returns the hash code of given program file
	 * @throws IOException 
	 */
	private int generateHashCode(File programA) throws IOException {
		String strProgramA = ReadFileToString.readFileToString(programA);
		return strProgramA.hashCode();
	}
	
	
	/**
	 * Returns true iff two computed hash codes are same
	 */
	private boolean compareHashCode() {
		return hashCodeA == hashCodeB;
	}
	
	/**
	 * Write the report according to if two computed hash codes are same
	 */
	private Report writeReport(Boolean isSame) {
		float score;
		String message;
		if (isSame) {
			score=100;
			message="The files uploaded have an exact match";
		} else {
			score=0;
			message="The files uploaded do not have an exact match";
		}
		Report r=new Report(ComparisonLayer.HASHCODE, score, message);
		return r;
	}
	
	/**
	 * private fields
	 */
	private int hashCodeA;
	private int hashCodeB;
}
