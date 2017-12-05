package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Given a source code file path, read the program into one String
 *
 */
public class ReadFileToString {
	
	/**
	 * Read file from the file path into a String (only used for test cases)
	 * @param filePath
	 * @return
	 */
	public static String readFileToString(String filePath){
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(filePath));
			
			char[] buf = new char[10];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
				buf = new char[1024];
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		return fileData.toString();
	}
	
	
	/**
	 * Read file content into a String
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static String readFileToString(File file) throws IOException{
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(file));
		
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}

		reader.close();
		return fileData.toString();
	}
}
