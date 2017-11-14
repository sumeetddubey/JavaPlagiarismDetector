package comparator.hashcode;

/**
 * HashCodeComparator checks if two programs are exactly same by using hash code
 */

public class HashCodeComparator implements IComparator {

	/**
	 * Returns generated report of given programs to PlagiarismDetector
	 */
	public String generateReport(String programA, String programB) {

		hashCodeA = generateHashCode(programA);
		hashCodeB = generateHashCode(programB);
		
		boolean isSame = compareHashCode();
		
		String generatedReport = writeReport(isSame);
		
		return generatedReport;
	}
	
	
	/**
	 * Returns the hash code of given program file
	 */
	
	private int generateHashCode(String program) {
		return program.hashCode();
	}
	
	
	/**
	 * Returns true iff two computed hash codes are same
	 */
	
	private boolean compareHashCode() {
		if(hashCodeA == hashCodeB) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Write the report according to if two computed hash codes are same
	 */
	
	private String writeReport(Boolean isSame) {
		if (isSame) {
			report = "100" + "\n" + "Summary: plagiarism is detected in layer 0!";
		} else {
			report = "0" + "\n" + "Summary: layer 0 is succeessfully passed!";
		}
		return report;
	}
	
	/**
	 * Constructors
	 */
	
	public HashCodeComparator() {}
	
	public HashCodeComparator(int hashCodeA, int hashCodeB) {
		this.setHashCodeA(hashCodeA);
		this.setHashCodeB(hashCodeB);
	}
	
	/**
	 * Getters and setters
	 */
	
	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public int getHashCodeA() {
		return hashCodeA;
	}

	public void setHashCodeA(int hashCodeA) {
		this.hashCodeA = hashCodeA;
	}

	public int getHashCodeB() {
		return hashCodeB;
	}

	public void setHashCodeB(int hashCodeB) {
		this.hashCodeB = hashCodeB;
	}

	/**
	 * private fields
	 */
	
	private String report;
	private int hashCodeA;
	private int hashCodeB;


}
