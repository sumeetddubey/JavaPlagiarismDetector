package utility;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import TestSamples.SampleFilePaths;

public class ReadFileToStringTests {
	
	@Test
	public void testReadValidFileToString() throws IOException {
		File fileWithForLoop = new File(SampleFilePaths.fileWithForLoop);
		String fileWithForLoopStr = ReadFileToString.readFileToString(fileWithForLoop);
		String expected = "package TestSamples;\r\n" + 
				"\r\n" + 
				"/**\r\n" + 
				" * This file is used to test the ASTComparator to mock up user input\r\n" + 
				" *  \r\n" + 
				" * @author Wenjun\r\n" + 
				" *\r\n" + 
				" */\r\n" + 
				"public class ProgramWithForLoop {\r\n" + 
				"	int num;\r\n" + 
				"	int x=10;\r\n" + 
				"	ProgramWithForLoop(int num){\r\n" + 
				"		this.num = num;\r\n" + 
				"	}\r\n" + 
				"	\r\n" + 
				"	public int getSum() {\r\n" + 
				"		int sum = 0;\r\n" + 
				"		\r\n" + 
				"		for (int i = 0; i < num; i++) {\r\n" + 
				"			int temp = num + num * 2;\r\n" + 
				"			temp = -temp;\r\n" + 
				"			sum += temp;\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		return sum;\r\n" + 
				"	}\r\n" + 
				"	\r\n" + 
				"	public static void main(String[] args) {\r\n" + 
				"		ProgramWithForLoop a = new ProgramWithForLoop(5);\r\n" + 
				"		int sum = a.getSum();\r\n" + 
				"		\r\n" + 
				"		System.out.println(sum);\r\n" + 
				"	}\r\n" + 
				"}\r\n";
		
		assertEquals(expected, fileWithForLoopStr);
	}
	
	@Test
	public void testReadValidFilePathToString() {
		String fileWithForLoop = ReadFileToString.readFileToString(SampleFilePaths.fileWithForLoop);
		String expected = "package TestSamples;\r\n" + 
				"\r\n" + 
				"/**\r\n" + 
				" * This file is used to test the ASTComparator to mock up user input\r\n" + 
				" *  \r\n" + 
				" * @author Wenjun\r\n" + 
				" *\r\n" + 
				" */\r\n" + 
				"public class ProgramWithForLoop {\r\n" + 
				"	int num;\r\n" + 
				"	int x=10;\r\n" + 
				"	ProgramWithForLoop(int num){\r\n" + 
				"		this.num = num;\r\n" + 
				"	}\r\n" + 
				"	\r\n" + 
				"	public int getSum() {\r\n" + 
				"		int sum = 0;\r\n" + 
				"		\r\n" + 
				"		for (int i = 0; i < num; i++) {\r\n" + 
				"			int temp = num + num * 2;\r\n" + 
				"			temp = -temp;\r\n" + 
				"			sum += temp;\r\n" + 
				"		}\r\n" + 
				"		\r\n" + 
				"		return sum;\r\n" + 
				"	}\r\n" + 
				"	\r\n" + 
				"	public static void main(String[] args) {\r\n" + 
				"		ProgramWithForLoop a = new ProgramWithForLoop(5);\r\n" + 
				"		int sum = a.getSum();\r\n" + 
				"		\r\n" + 
				"		System.out.println(sum);\r\n" + 
				"	}\r\n" + 
				"}\r\n";
		
		assertEquals(expected, fileWithForLoop);
	}

}
