package driver;

import java.io.*;

import comparator.functionSignature.Layer1Detection;
import comparator.hashcode.HashCodeComparator;
import interfaces.IComparator;
import utility.Report;

public class PlagiarismDetector {
	
	/**
	 * Returns the report that indicates whether happens or not. 
	 * @throws IOException 
	 */
	
	public Report[] generateFinalReport() throws IOException {
		
		Report report0 = layer0Report();
		reports[0]=report0;
		System.out.println(report0.getMessage());
		
		Report report1 = layer1Report();
		System.out.println(report1.getScore());
		reports[1]=report1;
		
		Report report2 = layer2Report();
		System.out.println(report1.getScore());
		reports[1]=report1;
		
		return reports;
	}

	/**
	 * @throws IOException 
	 * 
	 */
	private Report layer0Report() throws IOException {
		IComparator hashCodeComparator = new HashCodeComparator();
		return hashCodeComparator.generateReport(programA, programB);
	}
	

	/**
	 * @throws IOException 
	 * 
	 */
	private Report layer1Report() throws IOException {
		IComparator layer1 = new Layer1Detection();
		return layer1.generateReport(programA, programB);
	}
	
	/**
	 * @throws IOException 
	 * 
	 */
	private Report layer2Report() throws IOException {
//		IComparator layer2 = new ASTComparator();
		return layer2.generateReport(programA, programB);
	}

	
	/**
	 * Constructors
	 */
//	
//	public PlagiarismDetector() {
//		this.reports=new Report[NUMBER_OF_LAYERS];
//	}
////	
//	public PlagiarismDetector(String programA, String programB) {
//		this.setProgramA(programA);
//		this.setProgramB(programB);
//		this.reports=new Report[NUMBER_OF_LAYERS];
//	}
	
	public PlagiarismDetector(File fileA, File fileB) throws IOException {
		programA = fileA;
		programB = fileB;
		this.reports=new Report[NUMBER_OF_LAYERS];
	}
	

	/**
	 * Getters and setters
	 */

	public File getProgramB() {
		return programB;
	}

	public void setProgramB(File programB) {
		this.programB = programB;
	}

	public File getProgramA() {
		return programA;
	}

	public void setProgramA(File programA) {
		this.programA = programA;
	}
	
	/**
	 * Private fields
	 */
	
	private File programA;
	private File programB;
	private Report[] reports;
	private final int NUMBER_OF_LAYERS=3;
	
}
