package comparator.hashcode;

import java.io.*;

public class PlagiarismDetector {
	
	/**
	 * Returns the report that indicates whether happens or not. 
	 */
	
	public String generateFinalReport() {
		
		String report = "";
		
		if (isPlagiarized == false) {
			IComparator hashCodeComparator = new HashCodeComparator();
			report = hashCodeComparator.generateReport(programA, programB);
			String line1 = report.substring(0, report.indexOf("\n"));
			int score = Integer.valueOf(line1);
			if(score >= threshold) {
				isPlagiarized = true;
				return "Score: " + report;
			}
		}
		
		/**
		 * Function Signature comparison
		 */
		
		
		/**
		 * AST comparison
		 */
		

		return "Score: " + report;
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
	 * Constructors
	 */
	
	public PlagiarismDetector() {}
	
	public PlagiarismDetector(int threshold, boolean isPlagiarized, String programA, String programB) {
		this.setThreshold(threshold);
		this.setPlagiarized(isPlagiarized);
		this.setProgramA(programA);
		this.setProgramB(programB);
	}
	
	public PlagiarismDetector(File fileA, File fileB) throws IOException {
		programA = convertFileToString(fileA);
		programB = convertFileToString(fileB);
	}
	

	/**
	 * Getters and setters
	 */

	public String getProgramB() {
		return programB;
	}

	public void setProgramB(String programB) {
		this.programB = programB;
	}

	public String getProgramA() {
		return programA;
	}

	public void setProgramA(String programA) {
		this.programA = programA;
	}

	public boolean isPlagiarized() {
		return isPlagiarized;
	}

	public void setPlagiarized(boolean isPlagiarized) {
		this.isPlagiarized = isPlagiarized;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	
	/**
	 * Private fields
	 */
	
	private int threshold = 80;
	private boolean isPlagiarized = false;
	private String programA;
	private String programB;
	
}
