package ui;

import javafx.application.Application;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
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

public class PlagerismCheckerUI
        extends Application {

	private Text actionStatus;
	private Stage savedStage;
	private static final String titleTxt = "Plagerism Checker";
	File[] files = new File[2];
	int count=0;
	int countf=1;
	GridPane gridpane = new GridPane();
	VBox vbox = new VBox(30);
	Button btn1 = new Button("Choose File 1");
	Button btn2 = new Button("Choose File 2");

	/***
	 * launches the javafx gui
	 */
	public static void main(String [] args) {

		Application.launch(args);
	}

	/***
	 * starts the javafx gui
	 */
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

		btn1.setOnAction(new SingleFcButtonListener());
		HBox buttonHb1 = new HBox(10);
		buttonHb1.setAlignment(Pos.CENTER);
		buttonHb1.getChildren().addAll(btn1);

		btn2.setOnAction(new SingleFcButtonListener());
		HBox buttonHb2 = new HBox(10);
		buttonHb2.setAlignment(Pos.CENTER);
		buttonHb2.getChildren().addAll(btn2);

		Button btn3 = new Button("CheckPlagerism");
		btn3.setOnAction(new SaveButtonListener());
		HBox buttonHb3 = new HBox(10);
		buttonHb3.setAlignment(Pos.CENTER);
		buttonHb3.getChildren().addAll(btn3);

		// Status message text
		actionStatus = new Text();
		actionStatus.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
		actionStatus.setFill(Color.FIREBRICK);


		gridpane.setGridLinesVisible(true);

		Label label1 = new Label();
		label1.setText("File 1");
		Label label2 = new Label();
		label2.setText("File 2");
		label1.setAlignment(Pos.CENTER);
		label2.setAlignment(Pos.CENTER);
		gridpane.getColumnConstraints().add(new ColumnConstraints(400)); // column 0 is 100 wide
		gridpane.getColumnConstraints().add(new ColumnConstraints(400)); // column 1 is 100 wide
		// adding children to gridpane
		gridpane.add(label2, 0, 0); // column=0 row=0
		gridpane.add(label1, 1, 0);  // column=1 row=0
		gridpane.setAlignment(Pos.CENTER);
		gridpane.setPadding(new Insets(5, 5, 5, 5));

		// Vbox
		vbox.setPadding(new Insets(25, 25, 25, 25));;
		vbox.getChildren().addAll(labelHb, buttonHb1, buttonHb2,buttonHb3, actionStatus);

		// Scene
		Scene scene = new Scene(vbox, 500, 300); // w x h
		primaryStage.setScene(scene);
		primaryStage.show();

		savedStage = primaryStage;
	}


	private class SingleFcButtonListener implements EventHandler<ActionEvent> {

		/***
		 * listener for fileupload button
		 */
		@Override
		public void handle(ActionEvent e) {
			if(e.getSource()==btn1) {
				showSingleFileChooser();
				btn1.setDisable(true);
			}
			if(e.getSource()==btn2) {
				showSingleFileChooser();
				btn2.setDisable(true);
			}
		}
	}
	/***
	 * saves the uploadedfile locally
	 */
	private void showSingleFileChooser() {
	FileChooser fileChooser = new FileChooser();
			File selectedFile = fileChooser.showOpenDialog(null);
			if (selectedFile != null) {
				files[count] = selectedFile;
				count++;
				actionStatus.setText(actionStatus.getText()+"  File selected: " + selectedFile.getName());
			} else {
				actionStatus.setText("File selection cancelled.");
			}
		}

	private class SaveButtonListener implements EventHandler<ActionEvent> {
		/***
		 * listener for checkplagiarism button
		 */
		@Override
		public void handle(ActionEvent e) {

			CheckPlagerism();
		}
	}
	/***
	 * checks plagerism and shows report
	 */
	private void CheckPlagerism() {

		for (File f : files
				) {
			if (f != null) {
				try {
					saveFileRoutine(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
				actionStatus.setText(count + " File saved ");

			}
		}
		Label label = new Label();
		label.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
		label.setText("The report should be here:");
		vbox.getChildren().addAll(label);
	}
	/***
	 * saves sent file to a location
	 */
	private void saveFileRoutine(File file)
			throws IOException{
		// create a folder called uploads and then edit this path to your local till uploads
		String path = "src/resource";
		File bfile =new File(path+"aFile"+countf+".java");
		countf++;
		FileInputStream inStream = new FileInputStream(file);
		FileOutputStream outStream = new FileOutputStream(bfile);

		byte[] buffer = new byte[1024];

		int length;
		//copy the file content in bytes
		while ((length = inStream.read(buffer)) > 0){

			outStream.write(buffer, 0, length);
		}

		inStream.close();
		outStream.close();

		System.out.println("File is saved successful!");
	}
}