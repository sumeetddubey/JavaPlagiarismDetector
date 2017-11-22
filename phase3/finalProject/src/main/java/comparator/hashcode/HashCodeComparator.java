package comparator.hashcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import interfaces.IComparator;
import utility.Report;

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
		String strProgramA = convertFileToString(programA);
		return strProgramA.hashCode();
	}
	
	/**
	 * Returns the string representation of upload file
	 */
	
	private String convertFileToString(File file) throws IOException {
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringBuilder strBuilder = new StringBuilder();
		String str;

		while((str = bufferedReader.readLine()) != null) {
			strBuilder.append(str + "\n");
		}
		bufferedReader.close();
		String fileToString = strBuilder.toString();
		return fileToString;
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
	
	private Report writeReport(Boolean isSame) {
		Report r = new Report();
		r.setLayer("layer0");
		if (isSame) {
			r.setScore(100);
			r.setMessage("Plagiarism detected in layer 0: files are same");
		} else {
			r.setScore(0);
			r.setMessage("Plagiarism not detected in layer 0");
		}
		return r;
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
