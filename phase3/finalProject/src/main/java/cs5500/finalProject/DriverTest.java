package cs5500.finalProject;
import java.io.File;
import java.io.IOException;

import comparator.hashcode.PlagiarismDetector;

public class DriverTest {
	public static void main(String[] args) throws IOException {
		File fileA = new File("./src/main/java/resource/A.java");
		File fileB = new File("./src/main/java/resource/B.java");
		PlagiarismDetector plagiarismDetector = new PlagiarismDetector(fileA, fileB);
		String report = plagiarismDetector.generateFinalReport();
		System.out.println(report);
	}
}
