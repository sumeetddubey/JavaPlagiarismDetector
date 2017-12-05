package comparator.hashcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import interfaces.IComparator;
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
	 * Class constructor
	 */
	public HashCodeComparator() {}

	/**
	 * private fields
	 */
	
	private int hashCodeA;
	private int hashCodeB;
}
