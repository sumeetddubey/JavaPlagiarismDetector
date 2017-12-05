package driver;

import java.io.*;

import comparator.ast.ASTComparator;
import comparator.functionSignature.FunctionSignatureComparator;
import comparator.hashcode.HashCodeComparator;
import interfaces.IComparator;
import utility.Report;

public class PlagiarismDetector2 {
	
	/**
	 * Returns the report that indicates whether happens or not. 
	 * @throws IOException 
	 */
	public Report[] generateFinalReport() throws IOException {
//		generate report for hash code layer
		Report report0 = layer0Report();
		reports[0]=report0;
		
//		generate report for function signature layer
		Report report1 = layer1Report();
		reports[1]=report1;

//		generate report for ast layer
		Report report2 = layer2Report();
		reports[2] = report2;

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
		IComparator layer1 = new FunctionSignatureComparator();
		return layer1.generateReport(programA, programB);
	}

	/**
	 * @throws IOException 
	 * 
	 */
	private Report layer2Report() throws IOException {
		IComparator ASTComparator = new ASTComparator();
		return ASTComparator.generateReport(programA, programB);
	}
	
	/**
	 * Constructor
	 */
	public PlagiarismDetector2(File fileA, File fileB) throws IOException {
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
