package ui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import utility.Report;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.*;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import driver.PlagiarismDetector;

public class MainPage extends Application {

	private Text actionStatus;
	private Stage savedStage;
	private static final String titleTxt = "Plagerism Checker";
	public File[] files = new File[2];
	public int count=0;
	public int countf=1;
	public static void main(String [] args) {

		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
	
		primaryStage.setTitle(titleTxt);	

		// Window label
		Label label = new Label("Plagerism Detector");
		label.setTextFill(Color.DARKBLUE);
		label.setFont(Font.font("Calibri", FontWeight.BOLD, 36));
		HBox labelHb = new HBox();
		labelHb.setAlignment(Pos.CENTER);
		labelHb.getChildren().add(label);

		// Buttons
		Button btn1 = new Button("Choose File 1");
		btn1.setOnAction(new SingleFcButtonListener());
		HBox buttonHb1 = new HBox(10);
		buttonHb1.setAlignment(Pos.CENTER);
		buttonHb1.getChildren().addAll(btn1);

		Button btn2 = new Button("Choose File 2");
		btn2.setOnAction(new SingleFcButtonListener());
		HBox buttonHb2 = new HBox(10);
		buttonHb2.setAlignment(Pos.CENTER);
		buttonHb2.getChildren().addAll(btn2);

		// Button
		Button btn3 = new Button("Save as file...");
		btn3.setOnAction(new SaveButtonListener());
		HBox buttonHb3 = new HBox(10);
		buttonHb3.setAlignment(Pos.CENTER);
		buttonHb3.getChildren().addAll(btn3);
		// Status message text
		actionStatus = new Text();
		actionStatus.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
		actionStatus.setFill(Color.FIREBRICK);

		// Vbox
		VBox vbox = new VBox(30);
		vbox.setPadding(new Insets(25, 25, 25, 25));;
		vbox.getChildren().addAll(labelHb, buttonHb1, buttonHb2,buttonHb3, actionStatus);

		// Scene
		Scene scene = new Scene(vbox, 500, 300); // w x h
		primaryStage.setScene(scene);
		primaryStage.show();

		savedStage = primaryStage;
	}

	private class SingleFcButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			showSingleFileChooser();
		}
	}

	private void showSingleFileChooser() {

		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			files[count]=selectedFile;
			count++;
			actionStatus.setText("File selected: " + selectedFile.getName());
		}
		else {
			actionStatus.setText("File selection cancelled.");
		}
	}
	private class SaveButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			showSaveFileChooser();
		}
	}

	private void showSaveFileChooser() {

		if (files.length == 2) {

			try {
				saveFileRoutine();
			} catch (IOException e) {

				e.printStackTrace();
				return;
			}
		}
	}

	private void saveFileRoutine() throws IOException{

		File temp = new File("src/main/java/resource/temp.java");
	    BufferedReader br = null;
	    BufferedWriter bw = null;
	    for(File file: files) {
	    	br = new BufferedReader(new FileReader(file));
	        bw = new BufferedWriter(new FileWriter(temp));
	        String line;
	        while ((line = br.readLine()) != null) {
	           if (line.contains("package"))
	              line = "package resource;";
	           bw.write(line+"\n");
	        }
	        br.close();
	        bw.close();
			// Creates a new file and writes the txtArea contents into it
			File bfile =new File("src/main/java/resource/" +file.getName());
			countf+=1;
			FileInputStream inStream = new FileInputStream(temp);
			FileOutputStream outStream = new FileOutputStream(bfile);
			
			byte[] buffer = new byte[1024];

			int length;
			//copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0){
				outStream.write(buffer, 0, length);

			}

			inStream.close();
			outStream.close();
	    }
		
		System.out.println("File is saved successfully!");
		System.out.println(files[0].getName());
		System.out.println(files[1].getName());
		File fileA = new File("src/main/java/resource/" +files[0].getName());
		File fileB = new File("src/main/java/resource/" +files[1].getName());
		Process pro=Runtime.getRuntime().exec("javac " +files[0].getName(), null, new File("src/main/java/resource/"));
		pro=Runtime.getRuntime().exec("javac " +files[1].getName(), null, new File("src/main/java/resource/"));
		PlagiarismDetector pd = new PlagiarismDetector(fileA, fileB);
		Report[] reports = pd.generateFinalReport();
	}
}