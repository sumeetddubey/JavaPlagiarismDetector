package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class HelpPage {

    public static void display(String title){
        Stage window;
        Scene scene;
        Label lblCaption;
        TextArea textArea;
        VBox vBox;

        // Stage
        window = new Stage();
        // block input events or user interaction with other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setX(400);
        window.setY(400);
        window.setWidth(800);
        window.setHeight(450);
        window.setResizable(false);


        // TextArea
        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setPrefRowCount(100);        
        
        
        textArea.setText("Instructions for Users \n\n" +
                "How the Plagiarism Detector Works \n\n" +
                "\t1. Upload two Java files from your local machine.\n" +
                "\t2. Click on \"Check Plagiarism\" button.\n" +
                "\t3. After waiting for a second, you will see three results.\n\n" +
                "Understanding the Results\n\n" + 
                "\t* We use three kinds of code comparison: \n"
                + "\t\thash code comparison, function signature comparison, and abstract syntax tree comparison.\n" +
                "\t* Each comparison corresponds to one layer.\n" +
                "\t* Each score is calculated based on a different algorithm.\n" + 
                "\t* Each message is a summary for that layer's comparison.\n" + 
                "\t* The tables shown in the last layer indicate code blocks which have high similarity.\n\n");
        textArea.getStyleClass().add("text-area1");
        

        // Label
        lblCaption = new Label("User Help Page");
        lblCaption.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        lblCaption.setLabelFor(textArea);

        // Layout
        vBox = new VBox(10);
        vBox.getChildren().addAll(lblCaption, textArea);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));

        // Scene
        scene = new Scene(vBox, 100, 100);
        scene.getStylesheets().add("ui/plag.css");
        window.setScene(scene);
        // display the window and before going back, it needs to be closed
        window.showAndWait();
    }
}
