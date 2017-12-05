package comparator.hashcode;

import interfaces.IComparator;
import utility.Report;
import utility.Report.ComparisonLayer;

/**
 * HashCodeComparator checks if two programs are exactly same by using hash code
 */

public class HashCodeComparator implements IComparator {

	/**
	 * Returns generated report of given programs to PlagiarismDetector
	 */
	public Report generateReport(String programA, String programB) {
		boolean isSame;
		
		if(programA == null || programB == null) {
			return writeReport(false);
		}
		
		hashCodeA = programA.hashCode();
		hashCodeB = programB.hashCode();
		
		isSame = compareHashCode();
		
		return writeReport(isSame);
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
	
	private int hashCodeA; // hashCode for program A
	private int hashCodeB; // hashCode for program B
}
