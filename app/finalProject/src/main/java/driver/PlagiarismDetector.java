package driver;

import java.io.*;

import comparator.ast.ASTComparator;
import comparator.functionSignature.FunctionSignatureComparator;
import comparator.hashcode.HashCodeComparator;
import interfaces.IComparator;
import utility.ReadFileToString;
import utility.Report;

public class PlagiarismDetector {
	
	/**
	 * Returns the report that indicates whether happens or not. 
	 * @throws IOException 
	 */
	public Report[] generateFinalReport() {
//		generate report for hash code layer
		//Report report0 = layer0Report();
		reports[0]=layer0Report();
		
//		generate report for function signature layer
//		Report report1 = layer1Report();
		reports[1]=layer1Report();

//		generate report for ast layer
		//Report report2 = layer2Report();
		reports[2] = layer2Report();

		return reports;
	}

	/**
	 * @throws IOException 
	 * 
	 */
	private Report layer0Report(){
		IComparator hashCodeComparator = new HashCodeComparator();
		return hashCodeComparator.generateReport(programA, programB);
	}

	/**
	 * @throws IOException 
	 * 
	 */
	private Report layer1Report() {
		IComparator layer1 = new FunctionSignatureComparator();
		return layer1.generateReport(programA, programB);
	}

	/**
	 * @throws IOException 
	 * 
	 */
	private Report layer2Report() {
		IComparator ASTComparator = new ASTComparator();
		return ASTComparator.generateReport(programA, programB);
	}
	
	/**
	 * Constructor
	 */
	public PlagiarismDetector(File fileA, File fileB) {
		try {
			programA = ReadFileToString.readFileToString(fileA);
			programB = ReadFileToString.readFileToString(fileB);
			this.reports=new Report[NUMBER_OF_LAYERS];
		} catch (IOException e) {
		    throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * Private fields
	 */
	private String programA;
	private String programB;
	private Report[] reports;
	private final int NUMBER_OF_LAYERS=3;
	
}
